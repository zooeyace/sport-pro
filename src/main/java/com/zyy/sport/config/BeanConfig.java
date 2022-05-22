package com.zyy.sport.config;

import com.qiniu.storage.UploadManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    /**
     *  文件上传对象
     */
    @Bean
    public UploadManager getUploadManager() {
        com.qiniu.storage.Configuration configuration = new com.qiniu.storage.Configuration();
        return new UploadManager(configuration);
    }

}
