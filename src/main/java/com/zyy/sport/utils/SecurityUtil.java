package com.zyy.sport.utils;

import com.zyy.sport.entity.EasyUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    /**
     * 获取用户名
     */
    public static String getUsername() {
        try {
            return ((EasyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取用户ID
     */
    public static Integer getUserId() {
        try {
            return ((EasyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取用户信息
     */
    public static EasyUser getUserInfo() {
        try {
            EasyUser user = (EasyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user.setPassword(null);
            return user;
        } catch (Exception e) {
            return null;
        }
    }
}