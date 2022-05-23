package com.zyy.sport.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSONObject;
import com.zyy.sport.common.QueryInfo;
import com.zyy.sport.common.R;
import com.zyy.sport.entity.Food;
import com.zyy.sport.entity.FoodType;
import com.zyy.sport.service.FoodService;
import com.zyy.sport.utils.QiNiuUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/food")
public class FoodController {

    @Resource
    private FoodService foodService;

    @Resource
    private QiNiuUtil qiNiuUtil;

    /**
     *  通过excel批量导入
     */
    @PostMapping("/batchImport")
    public R batchImport(@RequestParam("file") MultipartFile file) {
        try {
            ImportParams params = new ImportParams();
            List<Food> list = ExcelImportUtil.importExcel(file.getInputStream(), Food.class, params);
            list.forEach(item -> {
                if (StringUtils.isNotEmpty(item.getImageUrls())) {
                    try {
                        FileInputStream inputStream = new FileInputStream(item.getImageUrls());
                        String uuid = UUID.randomUUID().toString().substring(0, 7);
                        int index = item.getImageUrls().lastIndexOf(".");
                        String suffix = item.getImageUrls().substring(index);
                        String fileName = uuid + suffix;
                        String upload = qiNiuUtil.upload(inputStream, fileName);
                        item.setImageUrls(upload);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            return foodService.batchImport(list);
        } catch (Exception e) {
            e.printStackTrace();
            return R.errorOf("食物列表导入失败！");
        }
    }

    @PostMapping("/type/insert")
    public R insertType(@RequestBody FoodType foodType) {
        return foodService.insertType(foodType);
    }

    @DeleteMapping("/type/delete/{id}")
    public R deleteType(@PathVariable Long id) {
        return foodService.deleteType(id);
    }

    @PostMapping("/type/update")
    public R updateType(@RequestBody FoodType foodType) {
        return foodService.updateType(foodType);
    }

    @PostMapping("/type/page")
    public R findPage(@RequestBody QueryInfo queryInfo) {
        return foodService.findPage(queryInfo);
    }

    @GetMapping("/types")
    public R typeAll() {
        return foodService.typeAll();
    }

    @PostMapping("/page")
    public R findFoodPage(@RequestBody QueryInfo queryInfo) {
        return foodService.findFoodPage(queryInfo);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(value = "页码", name = "pageNumber", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(value = "页数大小", name = "pageSize", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(value = "食物类别", name = "typeId", required = true, dataTypeClass = Long.class),
            @ApiImplicitParam(value = "关键字", name = "keywords", dataTypeClass = String.class)
    })
    @PostMapping("/mini/page")
    public R findMiniPage(@RequestBody JSONObject object) {
        return foodService.findMiniPage(object);
    }

    @PostMapping("/insert")
    public R insert(@RequestBody Food food) {
        return foodService.insert(food);
    }

    @PostMapping("/update")
    public R update(@RequestBody Food food) {
        return foodService.update(food);
    }

    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Long id) {
        return foodService.delete(id);
    }

    @PostMapping("/typeId")
    public R findFoodByTypeId(@RequestBody QueryInfo queryInfo) {
        return foodService.findFoodByTypeId(queryInfo);
    }

    @GetMapping("/{id}")
    public R foodInfo(@PathVariable Long id) {
        return foodService.findById(id);
    }

}