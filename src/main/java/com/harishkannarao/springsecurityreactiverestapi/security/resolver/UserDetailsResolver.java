package com.harishkannarao.springsecurityreactiverestapi.security.resolver;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class UserDetailsResolver {

    public Mono<UserDetails> resolve(String token) {
        if ("user-token".equals(token)) {
            return Mono.just(
                    User.builder()
                            .username("user-name-1")
                            .password("")
                            .disabled(false)
                            .accountExpired(false)
                            .credentialsExpired(false)
                            .accountLocked(false)
                            .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                            .build()
            );
        } else if ("admin-token".equals(token)) {
            return Mono.just(
                    User.builder()
                            .username("admin-name-1")
                            .password("")
                            .disabled(false)
                            .accountExpired(false)
                            .credentialsExpired(false)
                            .accountLocked(false)
                            .authorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")))
                            .build()
            );
        } else {
            return Mono.empty();
        }
    }
}
