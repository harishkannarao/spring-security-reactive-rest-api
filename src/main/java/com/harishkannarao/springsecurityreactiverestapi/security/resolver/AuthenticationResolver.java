package com.harishkannarao.springsecurityreactiverestapi.security.resolver;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import reactor.core.publisher.Mono;

public interface AuthenticationResolver {
    Mono<UsernamePasswordAuthenticationToken> resolve(ServerHttpRequest request);
}
