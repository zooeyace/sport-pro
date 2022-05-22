package com.zyy.sport.utils;

import com.zyy.sport.VO.MailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@Slf4j
public class MailUtil {

    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public boolean sendMail(MailVO mail) {
        try {
            if (mail.isUseHtml()) {
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                messageHelper.setFrom(from);
                messageHelper.setTo(mail.getReceivers());
                messageHelper.setSubject(mail.getSubject());
                messageHelper.setText(mail.getContent(), true);
                mailSender.send(mimeMessage);
                log.info("邮件发送成功");
            } else {
                // 创建邮件对象
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                // 发件人
                simpleMailMessage.setFrom(from);
                // 收件人
                simpleMailMessage.setTo(mail.getReceivers());
                // 主题
                simpleMailMessage.setSubject(mail.getSubject());
                // 内容
                simpleMailMessage.setText(mail.getContent());
                mailSender.send(simpleMailMessage);
                log.info("邮件[{}]发送成功", mail.getSubject());
            }
            return true;
        } catch (MailException | MessagingException e) {
            log.error("----邮件发送失败----");
            log.error(e.getMessage());
            return false;
        }
    }
}
