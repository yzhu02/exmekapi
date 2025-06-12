package com.exmek.core.springconfig;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.buf.EncodedSolidusHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.cors.CorsConfiguration;

import com.exmek.commons.utils.JsonMapperUtils;
import com.exmek.commons.utils.UrlUtils;
import com.exmek.core.config.AppConfigProvider;
import com.exmek.core.consts.EndpointConsts;
import com.exmek.core.exception.ErrorCode;
import com.exmek.core.exception.ErrorResponse;
import com.exmek.core.persistence.entity.UserEntity;
import com.exmek.core.persistence.repository.UserRepository;
import com.exmek.core.resource.ResourceManager;
import com.exmek.core.resource.UserResourceManager;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	private AppConfigProvider appConfigProvider;
	
	@Autowired
	private UserRepository userRepository;
		  
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSec) throws Exception {
    	httpSec
    	.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration corsConfig = new CorsConfiguration();
            corsConfig.setAllowedOrigins(appConfigProvider.getCorsAllowedOrigins());
            corsConfig.addAllowedMethod("*");
            corsConfig.addAllowedHeader("*");
            corsConfig.setAllowCredentials(true);
            return corsConfig;
        }))
    	.csrf(csrf -> csrf.disable())
    	.authorizeHttpRequests(auth -> {
    	    auth
    	    .requestMatchers(
    	    		UrlUtils.concatURL(EndpointConsts.ENDPOINT_API_PREFIX, "**"),
    	    		UrlUtils.concatURL(EndpointConsts.ENDPOINT_ADMIN_PREFIX, "**")
    	    		)
    	    .authenticated()
    	    ;
    	    
    		auth
    	    .requestMatchers(
    	    		UrlUtils.concatURL("/", ResourceManager.DIR_NAME_IMAGES, "**"),
    	    		UrlUtils.concatURL("/", ResourceManager.DIR_NAME_MATERIALS, "**"),
    	    		UrlUtils.concatURL("/", UserResourceManager.NEWSREPO_BASE_PATH, "**"),
    	    		UrlUtils.concatURL("/", UserResourceManager.COMMON_TECHDOCS_BASE_PATH, "**"),
    	    		EndpointConsts.ENDPOINT_HEALTH
    	    		)
    	    .permitAll()
    	    ;
    	})
    	.exceptionHandling(exceptions -> exceptions
    			.authenticationEntryPoint(authenticationEntryPoint())
    			.accessDeniedHandler(accessDeniedHandler())
    	)
    	.httpBasic(Customizer.withDefaults())
    	;
    	return httpSec.build();
    }
    
    @Bean
    AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ErrorResponse errorResp = ErrorResponse.builder()
            		.code(ErrorCode.ERR_CODE_UNAUTHORIZED)
            		.message(authException.getMessage()).build();
            response.getWriter().write(JsonMapperUtils.writeValueAsString(errorResp));
        };
    }
    
    @Bean
    AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ErrorResponse errorResp = ErrorResponse.builder()
            		.code(ErrorCode.ERR_CODE_FORBIDDEN)
            		.message(accessDeniedException.getMessage()).build();
            response.getWriter().write(JsonMapperUtils.writeValueAsString(errorResp));
        };
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    
    @Bean
    UserDetailsService userDetailsService() {
    	List<UserEntity> userEntities = userRepository.findAll();
    	List<UserDetails> userDetails = userEntities.stream()
    			.map(u -> User.builder()
    					.username(u.getUsername())
    					.password(passwordEncoder().encode(u.getPassword()))
    					.roles(u.getRole())
    					.build())
    			.collect(Collectors.toList());
        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
    ////// Below are the configuration to setAllowUrlEncodedSlash
//    @Override
//    void configurePathMatch(PathMatchConfigurer configurer) {
//    	UrlPathHelper urlPathHelper = new UrlPathHelper();
//    	urlPathHelper.setUrlDecode(false);
//    	configurer.setUrlPathHelper(urlPathHelper);
//    }

    @Bean
    HttpFirewall allowUrlEncodedSlashHttpFirewall() {
    	StrictHttpFirewall firewall = new StrictHttpFirewall();
    	if (Boolean.TRUE.equals(appConfigProvider.getAllowUrlEncodedSlash())) {
    		firewall.setAllowUrlEncodedSlash(true);
    	}
    	return firewall;
    }

    @Bean
    TomcatConnectorCustomizer connectorCustomizer() {
    	return new TomcatConnectorCustomizer() {
    		@Override
    		public void customize(Connector connector) {
    			if (Boolean.TRUE.equals(appConfigProvider.getAllowUrlEncodedSlash())) {
    				connector.setEncodedSolidusHandling(EncodedSolidusHandling.DECODE.getValue());
    			}
    		}
    	};
    }
    //////Above are the configuration to setAllowUrlEncodedSlash
}
