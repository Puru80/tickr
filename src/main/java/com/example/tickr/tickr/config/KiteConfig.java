package com.example.tickr.tickr.config;

import com.zerodhatech.kiteconnect.KiteConnect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class KiteConfig {

    @Value("${app.config.kite-api-key}")
    private String apiKey;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public KiteConnect kiteConnectBean() {
        KiteConnect kiteConnect = new KiteConnect(apiKey);
        kiteConnect.setSessionExpiryHook(() -> System.out.println("Kite session expired!!!"));
        return kiteConnect;
    }
}
