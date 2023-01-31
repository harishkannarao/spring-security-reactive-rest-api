package com.harishkannarao.springsecurityreactiverestapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.util.TestSocketUtils;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
abstract class AbstractBaseIntegrationTest {

    private static final int RANDOM_SERVER_PORT = TestSocketUtils.findAvailableTcpPort();

    @DynamicPropertySource
    static void registerTestProperties(DynamicPropertyRegistry registry) {
        registry.add("server.port", () -> String.valueOf(RANDOM_SERVER_PORT));
    }

    @Value("${test.application.url}")
    private String testApplicationUrl;

    protected String getTestApplicationUrl() {
        return testApplicationUrl;
    }

    protected TestRestTemplate testRestTemplate() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                .rootUri(testApplicationUrl);
        return new TestRestTemplate(restTemplateBuilder);
    }
}
