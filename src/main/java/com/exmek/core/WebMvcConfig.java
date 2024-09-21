package com.exmek.core;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.exmek.core.news.NewsRepo;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/" + NewsRepo.NEWSREPO_NAME + "/**")
                .addResourceLocations("classpath:/" + NewsRepo.NEWSREPO_NAME + "/");
    }
}