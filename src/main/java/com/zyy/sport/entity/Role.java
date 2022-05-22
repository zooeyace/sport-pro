package com.zyy.sport.entity;

import lombok.Data;

import java.util.List;

@Data
public class Role {

    private Integer id;

    private String name;

    private String remark;

    private boolean status;

    private List<Menu> menus;

    private List<Permission> permissions;
}
