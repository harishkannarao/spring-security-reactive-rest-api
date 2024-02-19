package com.harishkannarao.springsecurityreactiverestapi.controller;

import com.harishkannarao.springsecurityreactiverestapi.AbstractBaseIntegrationTestProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class FallBackRestControllerIntegrationTest extends AbstractBaseIntegrationTestProfile {

    private final TestRestTemplate testRestTemplate;

    @Autowired
    public FallBackRestControllerIntegrationTest(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    @Test
    public void test_nonExistentEndpoint_return401_forUnAuthenticatedRequest() {
        ResponseEntity<Void> result = testRestTemplate
                .getForEntity("/non-existent-api/some-path", Void.class);
        assertThat(result.getStatusCode().value()).isEqualTo(401);
    }

    @Test
    public void test_nonExistentEndpoint_return403_forAuthenticatedRequest() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth("user-token");
        HttpEntity<Void> requestEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<Void> result = testRestTemplate
                .exchange("/non-existent-api/some-path", HttpMethod.GET, requestEntity, Void.class);

        assertThat(result.getStatusCode().value()).isEqualTo(403);
    }
}
