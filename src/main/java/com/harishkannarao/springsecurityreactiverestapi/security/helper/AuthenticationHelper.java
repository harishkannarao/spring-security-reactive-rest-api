package com.harishkannarao.springsecurityreactiverestapi.security.helper;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationHelper {

    public Mono<String> getCurrentUsername() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(authentication -> ((UserDetails) authentication.getPrincipal()).getUsername());
    }

    public Mono<String> getCurrentUsername(ServerWebExchange serverWebExchange) {
        return serverWebExchange.getPrincipal()
                .map(principal ->
                        (UsernamePasswordAuthenticationToken) principal)
                .map(authentication ->
                        ((UserDetails) authentication.getPrincipal()).getUsername());
    }
}
