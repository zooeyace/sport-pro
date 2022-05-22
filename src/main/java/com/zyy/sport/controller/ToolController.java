package com.zyy.sport.controller;

import com.zyy.sport.VO.MailVO;
import com.zyy.sport.common.R;
import com.zyy.sport.service.UserService;
import com.zyy.sport.utils.MD5Util;
import com.zyy.sport.utils.MailUtil;
import com.zyy.sport.utils.QiNiuUtil;
import com.zyy.sport.utils.SmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Random;

@RestController
@RequestMapping("/tool")
@Slf4j
public class ToolController {

    @Resource
    private QiNiuUtil qiNiuUtil;

    @Resource
    private MailUtil mailUtil;

    @Resource
    private SmsUtil smsUtil;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserService userService;

    @PostMapping("/upload")
    public R upload(@RequestBody MultipartFile file) throws IOException {
        String url = qiNiuUtil.upload(file.getInputStream(), file.getOriginalFilename());
        return R.okOf(url, "文件上传成功");
    }

    /**
     * 通过邮件找回密码
     */
    @PostMapping("/retrieve/password")
    public R resetPwd(@RequestBody MailVO mail) {
        mail.setSubject("运动平台--密码找回");
        Random random = new Random();
        int num = 100000 + random.nextInt(100000);
        userService.updatePwdByMail(mail.getReceivers()[0], passwordEncoder.encode(MD5Util.md5(String.valueOf(num))));
        mail.setContent("您的新密码为: " + num);
        log.info("---- 新密码为: {} ----", num);
        return R.okOf(mailUtil.sendMail(mail), "邮件发送成功");
    }

    /**
     *  输入手机号 生成密码放置在redis
     */
    @PostMapping("/sms")
    public R sms(String phoneNumber) {
        smsUtil.sendMessage(phoneNumber);
        return R.okOf("短信验证码发送成功");
    }

}
