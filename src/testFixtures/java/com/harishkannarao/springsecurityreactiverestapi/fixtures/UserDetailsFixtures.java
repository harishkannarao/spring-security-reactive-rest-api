package com.harishkannarao.springsecurityreactiverestapi.fixtures;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class UserDetailsFixtures {
    public static UserDetails createAnUser() {
        return User.builder()
                .username("user-name-1")
                .password("")
                .disabled(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .accountLocked(false)
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
    }
}
