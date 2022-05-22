package com.zyy.sport.controller;

import com.zyy.sport.common.QueryInfo;
import com.zyy.sport.common.R;
import com.zyy.sport.entity.Role;
import com.zyy.sport.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @PostMapping("/page")
    public R page(@RequestBody QueryInfo queryInfo) {
        return roleService.page(queryInfo);
    }

    @PostMapping("/insert")
    public R insert(@RequestBody Role role) {
        return roleService.insert(role);
    }

    @PutMapping("/update")
    public R update(@RequestBody Role role) {
        return roleService.update(role);
    }

    @DeleteMapping("/delete/{id}")
    public R del(@PathVariable Integer id) {
        return roleService.delete(id);
    }

    @GetMapping("/all")
    public R all() {
        return roleService.all();
    }
}
