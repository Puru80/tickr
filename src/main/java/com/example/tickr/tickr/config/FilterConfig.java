package com.example.tickr.tickr.config;

import com.example.tickr.tickr.auth.AuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final AuthFilter authFilter;

    @Bean
    public FilterRegistrationBean<AuthFilter> registrationBean() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(authFilter);
        registrationBean.addUrlPatterns("/api/v1/*"); // Set the URL patterns to protect
        return registrationBean;
    }
}
