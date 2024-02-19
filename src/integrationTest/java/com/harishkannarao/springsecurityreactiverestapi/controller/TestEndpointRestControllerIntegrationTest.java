package com.harishkannarao.springsecurityreactiverestapi.controller;

import com.harishkannarao.springsecurityreactiverestapi.AbstractBaseIntegrationTestProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class TestEndpointRestControllerIntegrationTest extends AbstractBaseIntegrationTestProfile {

    private final TestRestTemplate testRestTemplate;

    @Autowired
    public TestEndpointRestControllerIntegrationTest(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    @Test
    public void testEndpoints_returns204() {
        ResponseEntity<Void> result = testRestTemplate
                .getForEntity("/test-endpoint", Void.class);

        assertThat(result.getStatusCode().value()).isEqualTo(204);
    }
}
