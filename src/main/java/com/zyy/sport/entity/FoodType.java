package com.zyy.sport.entity;

import lombok.Data;

import java.util.List;

@Data
public class FoodType {
    private Long id;

    private String title;

    private String icon;

    private List<Food> foods;
}