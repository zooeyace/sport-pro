package com.zyy.sport.VO;

import lombok.Data;

@Data
public class LoginVO {

    private String username;

    private String password;

    private String phoneNumber;

    private String smsCode;

    /**
     *  登录方式
     *      1 代表用户名-密码
     *      2 代表手机号-验证码
     */
    private Integer type;
}
