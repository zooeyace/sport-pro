package com.zyy.sport.controller;

import com.zyy.sport.common.QueryInfo;
import com.zyy.sport.common.R;
import com.zyy.sport.entity.EasyUser;
import com.zyy.sport.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/page")
    public R page(@RequestBody QueryInfo queryInfo) {
        return userService.page(queryInfo);
    }

    @PostMapping("/insert")
    public R insert(@RequestBody EasyUser user) {
        return userService.insert(user);
    }

    @PutMapping("/update")
    public R update(@RequestBody EasyUser user) {
        return userService.update(user);
    }

    @DeleteMapping("/delete/{id}")
    public R del(@PathVariable Integer id) {
        return userService.delete(id);
    }
}
