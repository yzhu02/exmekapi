package com.exmek.core.filter;

/**
 * Deprecated but just keep as placeholder for future
 */
//@Component
public class ConsumerAuthFilter { 
//extends OncePerRequestFilter {
	
//	@Autowired
//	private AppConfig appConfig;

//	@Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//    		throws ServletException, IOException {

//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		if (auth != null) {
//			filterChain.doFilter(request, response);
//			return;
//		}
//		List<Consumer> configuredConsumers = appConfig.getConsumers();
//		if (ObjectUtils.isEmpty(configuredConsumers)) {
//			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The 'consumers' is required to set up.");
//			return;
//		}
//		List<String> consumerIds = configuredConsumers.stream().map(Consumer::getId).collect(Collectors.toList());
//        String requestingConsumerId = request.getHeader("consumer.id");
//        if (ObjectUtils.isEmpty(requestingConsumerId)) {
//        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The request is missing consumer.id");
//            return;
//        }
//        if (!consumerIds.contains(requestingConsumerId)) {
//        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The request is passing invalid consumer.id");
//            return;
//        }
//        
//        org.springframework.security.core.Authentication authentication = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
//                new User("user", "user", Collections.emptyList()),
//                "user",
//                Collections.emptyList()
//        );
//        
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        
//        filterChain.doFilter(request, response);
//    }
}