package com.aluminium.acoe.app.batch.service.implementation;

import static com.aluminium.acoe.common.util.ApiUtil.callApi;
import static com.aluminium.acoe.common.util.CoordinateUtil.transformWGS84;

import com.aluminium.acoe.app.main.dto.CafeDto;
import com.aluminium.acoe.app.main.entity.Cafe;
import com.aluminium.acoe.app.main.entity.Franchise;
import com.aluminium.acoe.app.main.persistance.CafeRepository;
import com.aluminium.acoe.app.main.persistance.FranchiseRepository;
import com.aluminium.acoe.app.batch.service.BatchService;
import com.aluminium.acoe.app.main.service.CafeService;
import com.aluminium.acoe.common.dto.ApiDto;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgeo.proj4j.ProjCoordinate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BatchServiceImpl implements BatchService {
    public static final long AREA_CD = 3120000;
    public static final String URL = "http://openapi.seoul.go.kr:8088";

    public static final String TITLE = "LOCALDATA_072405_SM";
    @Value("${token.data_seoul}")
    private String token;

    private final CafeService cafeService;
    private final CafeRepository cafeRepository;
    private final FranchiseRepository franchiseRepository;

    @Override
    @Transactional
    public void executeCafeBatch() {
        // 카페 정보 조회
        Map<String, CafeDto> cafeMapByRefNo = cafeService.searchDtoList(AREA_CD, null)
            .stream().collect(Collectors.toMap(CafeDto::getRefNo, i->i));
        // 최종 디비에 저장할 카페 데이터 보관용
        List<Cafe> saveList = new ArrayList<>();
        // 프랜차이즈 목록 조회
        List<Franchise> franchises = franchiseRepository.findAllByUseYn(true);
        
        int start = 1;
        int end = 1000;
        int totalCnt = 1001;
        JSONArray arr = new JSONArray();
        try {
            while(totalCnt > end){
                JSONObject localData = callApi(buildApiDto(start, end)).getJSONObject(TITLE);
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

                // 좌표계 변환
                String strX = Objects.toString(row.get("X"),"0.0");
                String strY = Objects.toString(row.get("Y"),"0.0");
                double x = StringUtils.isBlank(strX) ? 0.0 : Double.parseDouble(strX);
                double y = StringUtils.isBlank(strY) ? 0.0 : Double.parseDouble(strY);
                ProjCoordinate projCoordinate = transformWGS84(x, y, "EPSG:2097");
                if (projCoordinate != null) {
                    cafeDto.setX(new BigDecimal(projCoordinate.x).setScale(6, RoundingMode.HALF_UP));
                    cafeDto.setY(new BigDecimal(projCoordinate.y).setScale(6, RoundingMode.HALF_UP));
                }

                // franchise setting
                String cafeNm = cafeDto.getCafeNm();
                Franchise matched = franchises.stream()
                        .filter(fran -> cafeNm.contains(fran.getFranchiseNm()))
                        .findFirst().orElseGet(() -> null);
                // 프랜차이즈인 경우 프랜차이즈 할인가격 세팅
                if(matched != null) cafeDto.setDiscountAmt(matched.getDiscountAmt());

                saveList.add(Cafe.toEntity(cafeDto, matched));
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }

        cafeRepository.saveAll(saveList);
    }

    private ApiDto buildApiDto(int startIdx, int endIdx){
        // 서대문구 only
        StringBuilder urlBuilder = new StringBuilder(URL); /*URL*/
        urlBuilder.append("/").append(URLEncoder.encode( token, StandardCharsets.UTF_8)); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
        urlBuilder.append("/").append(URLEncoder.encode("json", StandardCharsets.UTF_8)); /*요청파일타입 (xml,xmlf,xls,json) */
        urlBuilder.append("/").append(URLEncoder.encode(TITLE, StandardCharsets.UTF_8)); /*서비스명 (대소문자 구분 필수입니다.)*/
        urlBuilder.append("/").append(URLEncoder.encode(Objects.toString(startIdx), StandardCharsets.UTF_8)); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
        urlBuilder.append("/").append(URLEncoder.encode(Objects.toString(endIdx), StandardCharsets.UTF_8)); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/

        ApiDto apiDto = new ApiDto();
        apiDto.setUrl(Objects.toString(urlBuilder));
        apiDto.setMethod("GET");
        apiDto.setContentType("application/json");

        return apiDto;
    }
}