package com.zyy.sport.controller;

import com.zyy.sport.common.R;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    @PreAuthorize("hasAuthority('添加用户')")
    public R<String> test() {
        return R.okOf("success", "ok");
    }
}
