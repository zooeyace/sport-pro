package com.zyy.sport.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 *  Excel: name:列名   type: 导出类型 1是文本 ,2是图片, 3是函数,10 是数字 默认是文本
 */

@Data
public class Food {

    private Long id;

    @Excel(name = "食物名称")
    private String title;

    private Long typeId;

    @Excel(name = "食物图片", type = 2)
    private String imageUrls;

    @Excel(name = "营养元素")
    private String nutrient;

    @Excel(name = "热量")
    private Float heat;

    @Excel(name = "蛋白质")
    private Float protein;

    @Excel(name = "脂肪")
    private Float fat;

    @Excel(name = "碳水化合物")
    private Float carbonWater;

    @Excel(name = "膳食纤维")
    private Float dietaryFiber;

    @Excel(name = "维生素A")
    private Float vitaminA;

    @Excel(name = "维生素C")
    private Float vitaminC;

    @Excel(name = "维生素E")
    private Float vitaminE;

    @Excel(name = "胡萝卜素")
    private Float carrot;

    @Excel(name = "维生素B1")
    private Float vitaminB1;

    @Excel(name = "维生素B2")
    private Float vitaminB2;

    @Excel(name = "烟酸")
    private Float niacin;

    @Excel(name = "胆固醇")
    private Float cholesterol;

    @Excel(name = "镁")
    private Float magnesium;

    @Excel(name = "铁")
    private Float iron;

    @Excel(name = "钙")
    private Float calcium;

    @Excel(name = "锌")
    private Float zinc;

    @Excel(name = "铜")
    private Float copper;

    @Excel(name = "锰")
    private Float manganese;

    @Excel(name = "钾")
    private Float potassium;

    @Excel(name = "磷")
    private Float phosphorus;

    @Excel(name = "钠")
    private Float sodium;

    @Excel(name = "硒")
    private Float selenium;

    @JsonIgnore
    @Excel(name = "食物类别")
    private String typeTitle;
}