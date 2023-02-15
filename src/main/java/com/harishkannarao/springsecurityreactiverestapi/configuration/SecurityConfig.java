package com.harishkannarao.springsecurityreactiverestapi.configuration;

import com.harishkannarao.springsecurityreactiverestapi.security.filter.CustomAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationFilter customAuthenticationFilter;

    @Autowired(required = false)
    private List<Consumer<ServerHttpSecurity>> httpSecurityCustomizers;

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        Optional.ofNullable(httpSecurityCustomizers)
                .stream().flatMap(Collection::stream)
                .forEach(httpSecurityConsumer -> httpSecurityConsumer.accept(http));

        http
                .headers().hsts().and().and()
                .cors().and()
                .csrf().disable()
                .authorizeExchange(this::configureUrlAuthorization)
                .exceptionHandling()
                .accessDeniedHandler((exchange, denied) -> Mono.just(exchange.getResponse().setRawStatusCode(403)).then())
                .authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .addFilterBefore(customAuthenticationFilter, SecurityWebFiltersOrder.ANONYMOUS_AUTHENTICATION)
        ;

        return http.build();
    }

    private void configureUrlAuthorization(ServerHttpSecurity.AuthorizeExchangeSpec authExchange) {
        authExchange.pathMatchers("/general-data").permitAll();
        authExchange.pathMatchers("/user-data").hasAuthority("ROLE_USER");
        authExchange.pathMatchers("/admin/**").authenticated();

        authExchange.anyExchange().denyAll();
    }

    @Bean
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
    }
}
