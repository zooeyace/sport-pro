package com.zyy.sport.constant;

/**
 *  白名单
 */
public class SpringSecurityConstant {
    public static final String[] NONE_SECURITY_URL_PATTERNS = {

            // 前端
            "/favicon.ico",

            //swagger
            "/swagger-ui.html",
            "/doc.html",
            "/webjars/**",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/v2/api-docs-ext",
            "/configuration/ui",
            "/configuration/security",

            // 后端
            "/user/login",
            "/user/login/sms",
            "/user/logout",
            "tool/retrieve/password",
            "tool/sms",

            // 小程序
            "/mini/login",
    };
}
