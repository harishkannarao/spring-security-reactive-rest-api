package com.harishkannarao.springsecurityreactiverestapi.security.filter;

import com.harishkannarao.springsecurityreactiverestapi.security.exception.NoAuthenticationFoundException;
import com.harishkannarao.springsecurityreactiverestapi.security.resolver.CustomAuthenticationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomAuthenticationFilter implements WebFilter {

    private final CustomAuthenticationResolver customAuthenticationResolver;

    @Autowired
    public CustomAuthenticationFilter(CustomAuthenticationResolver customAuthenticationResolver) {
        this.customAuthenticationResolver = customAuthenticationResolver;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return customAuthenticationResolver.resolve(exchange.getRequest())
                .flatMap(authentication -> chain.filter(exchange).contextWrite(context -> ReactiveSecurityContextHolder.withAuthentication(authentication)))
                .onErrorResume(NoAuthenticationFoundException.class, e -> chain.filter(exchange))
                .onErrorResume(Exception.class, e -> {
                    log.error("Unexpected error during authentication", e);
                    return chain.filter(exchange);
                });
    }
}
