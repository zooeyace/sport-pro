package com.zyy.sport.controller;

import com.zyy.sport.common.QueryInfo;
import com.zyy.sport.common.R;
import com.zyy.sport.entity.Permission;
import com.zyy.sport.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @PostMapping("/page")
    public R page(@RequestBody QueryInfo queryInfo) {
        return permissionService.page(queryInfo);
    }

    @PostMapping("/insert")
    public R insert(@RequestBody Permission permission) {
        return permissionService.insert(permission);
    }

    @PutMapping("/update")
    public R update(@RequestBody Permission permission) {
        return permissionService.update(permission);
    }

    @DeleteMapping("/delete/{id}")
    public R del(@PathVariable Integer id) {
        return permissionService.delete(id);
    }

    @GetMapping("/all")
    public R findAll() {
        return permissionService.findAll();
    }
}
