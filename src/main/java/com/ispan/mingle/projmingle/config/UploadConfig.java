package com.ispan.mingle.projmingle.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UploadConfig {

    @Value("${upload.path}")
    private String path;

    public String getPath() {
        return path;
    }

}