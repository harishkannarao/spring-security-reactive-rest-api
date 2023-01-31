package com.harishkannarao.springsecurityreactiverestapi.configuration;

//@Configuration
//@EnableWebFluxSecurity
//@EnableReactiveMethodSecurity
public class SecurityConfig {

    /*@Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        http
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .headers().hsts().and().and()
                .cors().and()
                .csrf().disable()
                .authorizeExchange(this::configureUrlAuthorization)
                .exceptionHandling()
//                .accessDeniedHandler((request, response, accessDeniedException) -> response.setStatus(HttpStatus.FORBIDDEN.value()))
                .accessDeniedHandler((exchange, denied) -> Mono.just(exchange.getResponse().setRawStatusCode(403)).then())
                .authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
//                .addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }

    private void configureUrlAuthorization(ServerHttpSecurity.AuthorizeExchangeSpec authorizeExchangeSpec) {
        authorizeExchangeSpec.pathMatchers("/general-data").permitAll();
        authorizeExchangeSpec.anyExchange().permitAll();
    }*/

    /*@Bean
    CorsConfigurationSource corsConfigurationSource(@Value("${cors.origin.patterns}") String originPatterns) {
        List<String> originPatternList = Stream.of(originPatterns.split(",")).toList();
        List<String> methods = List.of("GET", "PUT", "POST", "DELETE", "OPTIONS", "PATCH", "TRACE");
        String urlPattern = "/**";
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(methods);
        configuration.setAllowedOriginPatterns(originPatternList);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(urlPattern, configuration);
        return source;
    }*/
}
