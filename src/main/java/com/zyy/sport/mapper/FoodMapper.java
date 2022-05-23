package com.zyy.sport.mapper;

import com.github.pagehelper.Page;
import com.zyy.sport.entity.Food;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FoodMapper {

    Page<Food> findPage(String queryParam);

    Food findByTitle(String title);

    void insert(Food food);

    void delete(Long id);

    void update(Food food);

    Page<Food> findByTypeId(String id);

    void insertList(@Param("foods") List<Food> foods);

    Page<Food> findMiniPage(@Param("typeId") Long typeId, @Param("keywords") String keywords);

    Food findById(Long id);
}
