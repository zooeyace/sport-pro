package com.zyy.sport.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zyy.sport.common.R;
import com.zyy.sport.entity.EasyUser;
import com.zyy.sport.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 小程序
 */

@Slf4j
@RestController
@RequestMapping("/mini")
public class MiniController {

    @Value("${mini.appid}")
    private String appid;

    @Value("${mini.secret}")
    private String secret;

    @Resource
    private UserService userService;


    @GetMapping("/login")
    public R login(String code) throws IOException {

        // 小程序端登录 code是必要的
        if (!StringUtils.isNotEmpty(code)) {
            return R.errorOf("无法登录");
        }
        // 构建get请求
        String url = "https://api.weixin.qq.com/sns/jscode2session?" + "appid=" +
                appid +
                "&secret=" +
                secret +
                "&js_code=" +
                code +
                "&grant_type=authorization_code";

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);

        // 发送请求
        CloseableHttpResponse response = client.execute(get);
        int statusCode = response.getStatusLine().getStatusCode();
        log.info("请求响应码: {}", statusCode);
        String result = EntityUtils.toString(response.getEntity());
        log.info("请求响应结果: {}", result);

        // 字符串转json
        JSONObject jsonObject = JSON.parseObject(result);
        String openid = jsonObject.getString("openid");
        log.info("小程序唯一标识 [openid]:  {}", openid);
        String sessionKey = jsonObject.getString("session_key");

        return userService.miniLogin(openid, sessionKey);
    }

    @PostMapping("/update/info")
    public R updateInfo(@RequestBody EasyUser user) {
        return userService.updateInfoByOpenid(user);
    }

}
