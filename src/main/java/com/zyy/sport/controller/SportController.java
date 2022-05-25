package com.zyy.sport.controller;

import com.zyy.sport.common.QueryInfo;
import com.zyy.sport.common.R;
import com.zyy.sport.entity.Sport;
import com.zyy.sport.service.SportService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sport")
public class SportController {

    @Resource
    private SportService sportService;

    @PostMapping("/page")
    public R findPage(@RequestBody QueryInfo queryInfo) {
        return sportService.findPage(queryInfo);
    }

    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        return sportService.delete(id);
    }

    @PostMapping("/insert")
    public R insert(@RequestBody Sport sport) {
        return sportService.insert(sport);
    }

    @PutMapping("/update")
    public R update(@RequestBody Sport sport) {
        return sportService.update(sport);
    }

    @GetMapping("/{id}")
    public R getInfo(@PathVariable Long id) {
        return sportService.findInfo(id);
    }

}