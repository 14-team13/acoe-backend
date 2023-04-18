package com.aluminium.acoe.app.cafe.service.implementation;

import static com.aluminium.acoe.common.api.ApiHelper.callApi;

import com.aluminium.acoe.app.cafe.dto.CafeDto;
import com.aluminium.acoe.app.cafe.entity.Cafe;
import com.aluminium.acoe.app.cafe.persistance.CafeRepository;
import com.aluminium.acoe.app.cafe.service.BatchService;
import com.aluminium.acoe.app.cafe.service.CafeService;
import com.aluminium.acoe.common.dto.ApiDto;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BatchServiceImpl implements BatchService {
    public static final long AREA_CD = 3120000;
    public static final String URL = "http://openapi.seoul.go.kr:8088";

    @Value("${data_seoul.token}")
    private String token;

    private final CafeService cafeService;
    private final CafeRepository cafeRepository;

    @Override
    @Transactional
    public void executeCafeBatch() {
        // 카페 정보 조회
        Map<String, CafeDto> cafeMapByRefNo = cafeService.searchList(AREA_CD, null)
            .stream().collect(Collectors.toMap(CafeDto::getRefNo, i->i));
        // 최종 디비에 저장할 카페 데이터 보관용
        List<Cafe> saveList = new ArrayList<>();
        
        int start = 1;
        int end = 1000;
        int totalCnt = 1001;
        JSONArray arr = new JSONArray();
        try {
            while(totalCnt > end){
                JSONObject localData = callApi(buildApiDto(start, end)).getJSONObject("LOCALDATA_072405_SM");
                totalCnt = (int) localData.get("list_total_count");

                start+= 1000;
                end+= 1000;

                for (int i = 0; i < localData.getJSONArray("row").length(); i++) {
                    arr.put(localData.getJSONArray("row").get(i));
                }
            }

            CafeDto cafeDto;
            for (int i = 0; i < arr.length(); i++) {
                cafeDto = new CafeDto();
                JSONObject row = arr.getJSONObject(i);
                // setting values
                cafeDto.setCafeId(cafeMapByRefNo.getOrDefault(Objects.toString(row.get("MGTNO"),""), new CafeDto()).getCafeId());
                cafeDto.setRefNo(Objects.toString(row.get("MGTNO"),""));
                cafeDto.setCafeNm(Objects.toString(row.get("BPLCNM"),""));
                cafeDto.setAreaCd(Long.valueOf(Objects.toString(row.get("OPNSFTEAMCODE"), "0")));
                cafeDto.setTrdStateCd(Long.valueOf(Objects.toString(row.get("TRDSTATEGBN"), "0")));
                cafeDto.setDtlStateCd(Long.valueOf(Objects.toString(row.get("DTLSTATEGBN"), "0")));
                cafeDto.setTelNo(Objects.toString(row.get("SITETEL"),""));
                cafeDto.setRoadAddr(Objects.toString(row.get("RDNWHLADDR"),""));
                cafeDto.setRoadPostNo(Objects.toString(row.get("RDNPOSTNO"),""));

                String x = Objects.toString(row.get("X"),"0");
                if(StringUtils.isNotBlank(x)) cafeDto.setX(new BigDecimal(Double.parseDouble(x)));
                String y = Objects.toString(row.get("Y"),"0");
                if(StringUtils.isNotBlank(y)) cafeDto.setY(new BigDecimal(Double.parseDouble(y)));

                saveList.add(Cafe.toEntity(cafeDto));
            }
        } catch (JSONException | IOException e){
            throw new RuntimeException(e);
        }

        cafeRepository.saveAll(saveList);
    }

    private ApiDto buildApiDto(int startIdx, int endIdx){
        // 서대문구 only
        StringBuilder urlBuilder = new StringBuilder(URL); /*URL*/
        try {
            urlBuilder.append("/").append(URLEncoder.encode( token, "UTF-8")); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
            urlBuilder.append("/").append(URLEncoder.encode("json", "UTF-8")); /*요청파일타입 (xml,xmlf,xls,json) */
            urlBuilder.append("/").append(URLEncoder.encode("LOCALDATA_072405_SM", "UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
            urlBuilder.append("/").append(URLEncoder.encode(Objects.toString(startIdx), "UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
            urlBuilder.append("/").append(URLEncoder.encode(Objects.toString(endIdx), "UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        ApiDto apiDto = new ApiDto();
        apiDto.setUrl(Objects.toString(urlBuilder));
        apiDto.setMethod("GET");
        apiDto.setContentType("application/json");

        return apiDto;
    }
}