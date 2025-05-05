package com.exmek.core.springconfig;

import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;
//import org.thymeleaf.spring6.SpringTemplateEngine;
//import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
//import org.thymeleaf.templateresolver.ITemplateResolver;

import com.exmek.core.config.AppConfigProvider;
import com.exmek.core.config.SmtpConf;

@Configuration
public class BeanContext {

	@Autowired
	private AppConfigProvider appConfigProvider;
	
    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

//    @Bean
//    SpringTemplateEngine templateEngine() {
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.addTemplateResolver(templateResolver());
//        return templateEngine;
//    }
//
//    private ITemplateResolver templateResolver() {
//        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
//        resolver.setPrefix("templates/");
//        resolver.setSuffix(".html");
//        resolver.setTemplateMode("HTML5");
//        resolver.setOrder(1);
//        resolver.setCacheable(true);
//        return resolver;
//    }

    @Bean
    JavaMailSender javaMailSender() {
    	SmtpConf smtpConf = appConfigProvider.getSmtpExmekSysConf();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();        
        mailSender.setHost(smtpConf.getHost());
        if (smtpConf.getPort() != null) {
        	mailSender.setPort(smtpConf.getPort());
        }
        if (smtpConf.getProtocol() != null) {
        	mailSender.setProtocol(smtpConf.getProtocol().name());
        }
        mailSender.setUsername(smtpConf.getUser());
        mailSender.setPassword(smtpConf.getPassword());
        Map<String, String> propsMap = smtpConf.getProperties();
        if (propsMap != null) {
        	Properties props = mailSender.getJavaMailProperties();
        	propsMap.forEach((k, v) -> props.put(k, v));
        }
        return mailSender;
    }
}
