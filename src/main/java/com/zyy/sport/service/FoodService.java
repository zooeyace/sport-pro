package com.zyy.sport.service;

import com.alibaba.fastjson.JSONObject;
import com.zyy.sport.common.QueryInfo;
import com.zyy.sport.common.R;
import com.zyy.sport.entity.Food;
import com.zyy.sport.entity.FoodType;

import java.util.List;

public interface FoodService {

    R findPage(QueryInfo queryInfo);

    R insert(Food food);

    R delete(Long id);

    R update(Food food);

    R updateType(FoodType foodType);

    R deleteType(Long id);

    R insertType(FoodType foodType);

    /**
     * 批量导入
     */
    R batchImport(List<Food> list);


    R findFoodPage(QueryInfo queryInfo);

    /**
     *  所有分类
     */
    R typeAll();

    R findFoodByTypeId(QueryInfo queryInfo);

    R findMiniPage(JSONObject object);

    R findById(Long id);
}