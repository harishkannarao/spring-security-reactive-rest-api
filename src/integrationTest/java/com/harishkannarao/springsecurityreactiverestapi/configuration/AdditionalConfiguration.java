package com.harishkannarao.springsecurityreactiverestapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;

import java.util.function.Consumer;

@Configuration
public class AdditionalConfiguration {

    @Bean
    public Consumer<ServerHttpSecurity> testEndpointCustomizer() {
        return http -> http.authorizeExchange((auth) ->
                auth.pathMatchers("/test-endpoint").permitAll());
    }
}
