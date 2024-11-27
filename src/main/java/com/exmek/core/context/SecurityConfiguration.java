package com.exmek.core.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.exmek.core.filter.ConsumerAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	  
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	ConsumerAuthFilter consumerAuthFilter = applicationContext.getBean(ConsumerAuthFilter.class);
    	http.addFilterBefore(consumerAuthFilter, UsernamePasswordAuthenticationFilter.class);
    	return http.build();
    }
}
