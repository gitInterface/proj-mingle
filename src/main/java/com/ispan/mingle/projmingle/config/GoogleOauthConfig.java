package com.ispan.mingle.projmingle.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Configuration
@PropertySource("google-oauth2.properties")  // 把該檔案放到 resourse 底下
@Data
public class GoogleOauthConfig {

    @Value("${client_id}")
    private String clientId;

    @Value("${client_secret}")
    private String clientSecret;

    @Value("${redirect_uris}")
    private String redirectUri;

}
