package com.zyy.sport.controller;

import com.zyy.sport.VO.LoginVO;
import com.zyy.sport.common.R;
import com.zyy.sport.service.UserService;
import com.zyy.sport.utils.RedisUtil;
import com.zyy.sport.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;

@RestController
@RequestMapping("/user")
@Slf4j
public class AuthenticationController {

    @Resource
    private UserService userService;

    @Resource
    private RedisUtil redisUtil;

    @PostMapping("/login")
    public R login(@RequestBody LoginVO loginVO) {
        return userService.login(loginVO);
    }

    @GetMapping("/userinfo")
    public R userinfo() {
        return R.okOf(SecurityUtil.getUserInfo(), "获取用户信息成功");
    }

    @GetMapping("/logout")
    public R<String> logout() {
        // 删除缓存
        redisUtil.delKey("userinfo_" + SecurityUtil.getUsername());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication) {
            // 存在用户信息，清空
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return R.okOf("退出成功");
    }
}