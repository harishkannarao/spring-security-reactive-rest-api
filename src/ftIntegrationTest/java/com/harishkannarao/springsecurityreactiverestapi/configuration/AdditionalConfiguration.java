package com.harishkannarao.springsecurityreactiverestapi.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdditionalConfiguration {

    @Bean
    protected TestRestTemplate testRestTemplate(
            @Value("${test.application.url}") String testApplicationUrl
    ) {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                .rootUri(testApplicationUrl);
        return new TestRestTemplate(restTemplateBuilder);
    }
}
