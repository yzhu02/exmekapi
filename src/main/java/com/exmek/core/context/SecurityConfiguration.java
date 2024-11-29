package com.exmek.core.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

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
    SecurityFilterChain filterChain(HttpSecurity httpSec) throws Exception {
    	ConsumerAuthFilter consumerAuthFilter = applicationContext.getBean(ConsumerAuthFilter.class);
    	httpSec
    	.addFilterBefore(consumerAuthFilter, UsernamePasswordAuthenticationFilter.class)
    	.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration corsConfig = new CorsConfiguration();
            corsConfig.setAllowedOrigins(appConfig.getCorsAllowedOrigins());
            corsConfig.addAllowedMethod("*");
            corsConfig.addAllowedHeader("*");
            corsConfig.setAllowCredentials(true);
            return corsConfig;
        }))
    	.csrf(csrf -> csrf.disable())
    	.authorizeHttpRequests(auth -> {
    	    auth
    	    .requestMatchers("/api/**")
    	    .permitAll()
    	    .anyRequest()
    	    .authenticated();
    	})
    	.httpBasic(Customizer.withDefaults());
    	return httpSec.build();
    }
}
