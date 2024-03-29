package com.harishkannarao.springsecurityreactiverestapi.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
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

    @Bean
    protected TestRestTemplate testRestTemplate(
            @Value("${test.application.url}") String testApplicationUrl
    ) {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                .rootUri(testApplicationUrl);
        return new TestRestTemplate(restTemplateBuilder);
    }
}
