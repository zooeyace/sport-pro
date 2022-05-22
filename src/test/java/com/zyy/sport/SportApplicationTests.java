package com.zyy.sport;

import com.zyy.sport.VO.MailVO;
import com.zyy.sport.utils.MD5Util;
import com.zyy.sport.utils.MailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@SpringBootTest
class SportApplicationTests {

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private MailUtil mailUtil;

    @Test
    void contextLoads() {
        System.out.println(bCryptPasswordEncoder.encode(MD5Util.md5("123456")));
        System.out.println(bCryptPasswordEncoder.matches(MD5Util.md5("123456"), bCryptPasswordEncoder.encode(MD5Util.md5("123456"))));
    }

    @Test
    void testMail() {
        MailVO mail = new MailVO();
        mail.setReceivers(new String[] {"3292177246@qq.com"});
        mail.setSubject("运动平台邮件测试");
        mail.setContent("test test test");
        System.out.println(mailUtil.sendMail(mail));
    }

}
