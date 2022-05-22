package com.zyy.sport.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Random;

/**
 *  短信
 */

@Component
@Slf4j
public class SmsUtil {

    @Resource
    private RedisUtil redisUtil;

    public void sendMessage(String phoneNumber) {
        Random random = new Random();
        int code = 100000 + random.nextInt(89999);
        log.info("---- 生成短信验证码: {} ----", code);
        redisUtil.setObjectWithTime(phoneNumber + "_sms_code", code, 5);
    }

}
