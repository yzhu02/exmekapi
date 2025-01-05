package com.exmek.core.springconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.exmek.commons.utils.UrlUtils;
import com.exmek.core.news.NewsRepo;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(UrlUtils.concatURL("/", NewsRepo.NEWSREPO_NAME, "**"))
                .addResourceLocations(UrlUtils.concatURL("classpath:", NewsRepo.NEWSREPO_NAME, "/"));
    }
    
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }
}