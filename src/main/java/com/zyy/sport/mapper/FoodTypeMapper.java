package com.zyy.sport.mapper;

import com.github.pagehelper.Page;
import com.zyy.sport.entity.FoodType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FoodTypeMapper {

    void insert(FoodType foodType);

    void delete(Long id);

    void update(FoodType foodType);

    Page<FoodType> findPage(String queryParam);

    FoodType findByTitle(String title);

    List<FoodType> typeAll();
}
