package com.harishkannarao.springsecurityreactiverestapi.security.resolver;

import com.harishkannarao.springsecurityreactiverestapi.domain.UserData;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UserDataResolver {

    public Mono<UserData> resolve(String username) {
        if ("user-name-1".equals(username)) {
            return Mono.just(UserData.builder()
                    .firstName("userFirstName")
                    .lastName("userLastName")
                    .build());
        } else {
            return Mono.empty();
        }
    }
}
