package com.aluminium.acoe.common.dto;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

@Data
public class ApiDto implements Serializable {

    private String url;
    private String method;
    private String contentType;
    private Map<String, String> requestHeaders;

}