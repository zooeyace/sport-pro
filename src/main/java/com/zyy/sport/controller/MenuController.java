package com.zyy.sport.controller;

import com.zyy.sport.common.QueryInfo;
import com.zyy.sport.common.R;
import com.zyy.sport.entity.Menu;
import com.zyy.sport.service.MenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @PostMapping("/page")
    public R page(@RequestBody QueryInfo queryInfo) {
        return menuService.page(queryInfo);
    }

    @GetMapping("/parentMenu")
    public R parentMenu() {
        return menuService.findParent();
    }

    @PostMapping("/insert")
    public R insert(@RequestBody Menu menu) {
        return menuService.insert(menu);
    }

    @PutMapping("/update")
    public R update(@RequestBody Menu menu) {
        return menuService.update(menu);
    }

    @DeleteMapping("/delete/{id}")
    public R del(@PathVariable Integer id) {
        return menuService.delete(id);
    }
}
