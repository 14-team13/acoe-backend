package com.aluminium.acoe.common.util;

import com.aluminium.acoe.common.dto.ApiDto;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import org.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
public class ApiUtil {
    public static JSONObject callApi(ApiDto apiDto) throws IOException {
        Objects.requireNonNull(apiDto.getUrl(), "url은 필수입니다.");
        Objects.requireNonNull(apiDto.getMethod(), "method는 필수입니다.");
        Objects.requireNonNull(apiDto.getContentType(), "contentType은 필수입니다.");

        // 연결 시작
        URL url = new URL(apiDto.getUrl());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(apiDto.getMethod());
        conn.setRequestProperty("Content-type", apiDto.getContentType());

        if(apiDto.getRequestHeaders() != null) {
            for(Map.Entry<String, String> header :apiDto.getRequestHeaders().entrySet()) {
                conn.setRequestProperty(header.getKey(), header.getValue());
            }
        }

        log.info("Response status : " + conn.getResponseCode());

        BufferedReader rd;
        // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        // response 읽기 시작
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        // 읽기 종료
        rd.close();

        // 연결 종료
        conn.disconnect();

        return new JSONObject(sb.toString());
    }
}
