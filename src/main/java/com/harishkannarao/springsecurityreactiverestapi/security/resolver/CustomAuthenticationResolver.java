package com.harishkannarao.springsecurityreactiverestapi.security.resolver;

import com.harishkannarao.springsecurityreactiverestapi.security.exception.NoAuthenticationFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class CustomAuthenticationResolver implements AuthenticationResolver {

    private static final String BEARER_PREFIX = "Bearer ";

    private final UserDetailsResolver userDetailsResolver;

    @Autowired
    public CustomAuthenticationResolver(UserDetailsResolver userDetailsResolver) {
        this.userDetailsResolver = userDetailsResolver;
    }

    public Mono<UsernamePasswordAuthenticationToken> resolve(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        Mono<String> token = extractToken(authHeader);
        Mono<UserDetails> userDetails = token.flatMap(userDetailsResolver::resolve);
        return userDetails.map(user -> new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()))
                .switchIfEmpty(Mono.error(new NoAuthenticationFoundException()));
    }

    private Mono<String> extractToken(String authHeader) {
        if (Objects.nonNull(authHeader) && authHeader.startsWith(BEARER_PREFIX)){
            return Mono.just(authHeader.substring(7));
        } else {
            return Mono.empty();
        }
    }
}
