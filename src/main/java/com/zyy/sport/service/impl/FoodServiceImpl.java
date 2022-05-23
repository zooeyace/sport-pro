package com.zyy.sport.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zyy.sport.common.PageResult;
import com.zyy.sport.common.QueryInfo;
import com.zyy.sport.common.R;
import com.zyy.sport.entity.Food;
import com.zyy.sport.entity.FoodType;
import com.zyy.sport.mapper.FoodMapper;
import com.zyy.sport.mapper.FoodTypeMapper;
import com.zyy.sport.service.FoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FoodServiceImpl implements FoodService {

    @Resource
    private FoodTypeMapper foodTypeMapper;

    @Resource
    private FoodMapper foodMapper;

    @Override
    public R delete(Long id) {
        foodMapper.delete(id);
        return R.okOf("食物删除成功");
    }

    @Override
    public R update(Food food) {
        foodMapper.update(food);
        return R.okOf("食物修改成功");
    }

    @Override
    public R insert(Food food) {
        foodMapper.insert(food);
        return R.okOf("食物添加成功");
    }

    @Override
    public R findPage(QueryInfo queryInfo) {
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<FoodType> page = foodTypeMapper.findPage(queryInfo.getQueryParam());
        return R.okOf(new PageResult<>(page.getTotal(), page.getResult()));
    }

    @Override
    public R findFoodPage(QueryInfo queryInfo) {
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<Food> page = foodMapper.findPage(queryInfo.getQueryParam());
        return R.okOf(new PageResult<>(page.getTotal(), page.getResult()));
    }

    @Override
    public R updateType(FoodType foodType) {
        foodTypeMapper.update(foodType);
        return R.okOf("菜品分类更新成功");
    }

    @Override
    public R deleteType(Long id) {
        foodTypeMapper.delete(id);
        return R.okOf("菜品分类删除成功");
    }

    @Override
    public R insertType(FoodType foodType) {
        foodTypeMapper.insert(foodType);
        return R.okOf("菜品分类添加成功");
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public R batchImport(List<Food> list) {
        List<Food> foods = new ArrayList<>();
        // 1. 读取分类
        for (Food food : list) {
            FoodType foodType = foodTypeMapper.findByTitle(food.getTypeTitle());
            Food title = foodMapper.findByTitle(food.getTitle());
            if (null != foodType) {
                // 修改
                if (title != null) {
                    foodMapper.update(food);
                } else {
                    // 添加
                    food.setTypeId(foodType.getId());
                    foods.add(food);
                }
            }
        }
        foodMapper.insertList(foods);
        return R.okOf("批量导入成功！");
    }

    @Override
    public R typeAll() {
        return R.okOf(foodTypeMapper.typeAll(), "分类查询成功！");
    }

    @Override
    public R findFoodByTypeId(QueryInfo queryInfo) {
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        String queryString = queryInfo.getQueryParam();
        Page<Food> foods = foodMapper.findByTypeId(queryString);
        return R.okOf(new PageResult<>(foods.getTotal(), foods.getResult()));
    }

    @Override
    public R findMiniPage(JSONObject object) {
        PageHelper.startPage(object.getInteger("pageNumber"), object.getInteger("pageSize"));
        Long typeId = object.getLong("typeId");
        String keywords = object.getString("keywords");
        Page<Food> foods = foodMapper.findMiniPage(typeId, keywords);
        return R.okOf(new PageResult<>(foods.getTotal(), foods.getResult()));
    }

    @Override
    public R findById(Long id) {
        if (id == null) {
            return R.errorOf("请传递食物编号");
        }
        return R.okOf(foodMapper.findById(id), "食物信息查询成功");
    }
}