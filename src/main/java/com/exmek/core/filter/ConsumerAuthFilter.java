package com.exmek.core.filter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.exmek.core.config.AppConfig;
import com.exmek.core.config.Consumer;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ConsumerAuthFilter extends OncePerRequestFilter {

//	private static final Logger logger = LoggerFactory.getLogger(ConsumerAuthFilter.class);
	
	@Autowired
	private AppConfig appConfig;

	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    		throws ServletException, IOException {

		List<Consumer> configuredConsumers = appConfig.getConsumers();
		if (ObjectUtils.isEmpty(configuredConsumers)) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The 'consumers' is required to set up.");
			return;
		}
		List<String> consumerIds = configuredConsumers.stream().map(Consumer::getId).collect(Collectors.toList());
        String requestingConsumerId = request.getHeader("consumer.id");
        if (ObjectUtils.isEmpty(requestingConsumerId)) {
        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The request is missing consumer.id");
            return;
        }
        if (!consumerIds.contains(requestingConsumerId)) {
        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The request is passing invalid consumer.id");
            return;
        }
        filterChain.doFilter(request, response);
    }
}