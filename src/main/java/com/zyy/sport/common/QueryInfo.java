package com.zyy.sport.common;

import lombok.Data;

@Data
public class QueryInfo {
    private String queryParam;

    private Integer pageNumber;

    private Integer pageSize;
}
