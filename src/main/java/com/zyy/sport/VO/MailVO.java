package com.zyy.sport.VO;

import lombok.Data;

import java.io.Serializable;

/**
 *  发送邮件传输实体类
 */

@Data
public class MailVO implements Serializable {

    /**
     *  是否使用html格式
     */
    private boolean useHtml;

    /**
     *  接收人
     */
    private String[] receivers;

    /**
     *  邮件主题
     */
    private String subject;

    /**
     *  邮件内容
     */
    private String content;
}
