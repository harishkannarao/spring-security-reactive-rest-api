package com.harishkannarao.springsecurityreactiverestapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        http
                .headers().hsts().and().and()
                .cors().and()
                .csrf().disable()
                .authorizeExchange(this::configureUrlAuthorization)
                .exceptionHandling()
                .accessDeniedHandler((exchange, denied) -> Mono.just(exchange.getResponse().setRawStatusCode(403)).then())
                .authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
//                .addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }

    private void configureUrlAuthorization(ServerHttpSecurity.AuthorizeExchangeSpec authorizeExchangeSpec) {
        authorizeExchangeSpec.pathMatchers("/general-data").permitAll();
        authorizeExchangeSpec.anyExchange().denyAll();
    }

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
