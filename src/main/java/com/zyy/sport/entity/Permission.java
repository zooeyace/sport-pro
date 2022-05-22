package com.zyy.sport.entity;

import lombok.Data;

@Data
public class Permission {
    private Integer id;

    private String name;

    private String permission;

    private boolean status;
}
