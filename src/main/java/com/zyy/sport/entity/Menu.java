package com.zyy.sport.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;


@Data
public class Menu {
    private int id;

    private String title;

    private boolean status;

    private String path;

    private String icon;

    private String component;

    @JSONField(serialize = false)
    private Integer parentId;

    private List<Menu> children;
}
