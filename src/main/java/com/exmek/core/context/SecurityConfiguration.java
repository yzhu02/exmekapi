package com.exmek.core.context;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.exmek.core.config.AppConfig;
import com.exmek.core.filter.ConsumerAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Autowired
	private AppConfig appConfig;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	  
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	ConsumerAuthFilter consumerAuthFilter = applicationContext.getBean(ConsumerAuthFilter.class);
    	http.addFilterBefore(consumerAuthFilter, UsernamePasswordAuthenticationFilter.class);
    	http.cors(Customizer.withDefaults());
    	return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(appConfig.getCorsAllowedOrigins());
        corsConfig.setAllowedMethods(Arrays.asList(
        		HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name()));
        corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "*"));
        corsConfig.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }
}
