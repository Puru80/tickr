package com.example.tickr.tickr.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "app.config")
public class AppConfig {

    private String kiteApiKey;
    private String kiteApiSecret;
    private String kiteUserId;
    private String kitePassword;

}
