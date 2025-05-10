package com.exmek.core.springconfig;

//import java.io.File;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import com.exmek.commons.utils.UrlUtils;
//import com.exmek.core.resource.ResourceManager;
//import com.exmek.core.resource.UserResourceManager;

//@Configuration
public class WebMvcConfig 
//implements WebMvcConfigurer 
{

//	@Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler(UrlUtils.concatURL("/", ResourceManager.DIR_NAME_IMAGES, "**"))
//        .addResourceLocations("file:" + UserResourceManager.RESOURCE_BASE_LOCATION + File.separator + ResourceManager.DIR_NAME_IMAGES + File.separator, "classpath:/static/images/");
//		
//		registry.addResourceHandler(UrlUtils.concatURL("/", ResourceManager.DIR_NAME_MATERIALS, "**"))
//        .addResourceLocations("file:" + UserResourceManager.RESOURCE_BASE_LOCATION + File.separator + ResourceManager.DIR_NAME_MATERIALS + File.separator, "classpath:/static/materials/");
//				
//        registry.addResourceHandler(UrlUtils.concatURL(UserResourceManager.NEWSREPO_BASE_PATH, "**"))
//        .addResourceLocations("file:" + UserResourceManager.NEWSREPO_LOCATION);
//
//        registry.addResourceHandler(UrlUtils.concatURL(UserResourceManager.COMMON_TECHDOCS_BASE_PATH, "**"))
//        .addResourceLocations("file:" + UserResourceManager.COMMON_TECHDOCS_LOCATION);
//    }
//    
//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        configurer.defaultContentType(MediaType.APPLICATION_JSON);
//    }
}