package com.zyy.sport.entity;

import lombok.Data;

/**
 *  运动简介
 */

@Data
public class Sport {

    private Long id;

    private String title;

    private String content;

    private String createTime;

    private String createName;

    private String updateTime;

    private String updateName;

    /**
     *  删除标记
     */
    private boolean del;

}