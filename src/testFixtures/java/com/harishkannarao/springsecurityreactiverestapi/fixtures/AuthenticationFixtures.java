package com.harishkannarao.springsecurityreactiverestapi.fixtures;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticationFixtures {
    public static Authentication createAuthentication(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
