package com.example.smartcitycloud.bean;

import com.example.smartcitycloud.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {// 登录校验
        List<String> excludePaths = Arrays.asList("/login", "/logout", "/test", "/error", "/users/*",
                "/tmpl/oapreview/**");
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns(excludePaths);
    }
}
