package com.harishkannarao.springsecurityreactiverestapi.controller;

import com.harishkannarao.springsecurityreactiverestapi.AbstractBaseIntegrationTestProfile;
import com.harishkannarao.springsecurityreactiverestapi.domain.UserData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AdminRestControllerIntegrationTest extends AbstractBaseIntegrationTestProfile {

    private final TestRestTemplate testRestTemplate;

    @Autowired
    public AdminRestControllerIntegrationTest(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    @Test
    public void test_getUserData_returns200_forAdminUser() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth("admin-token");
        HttpEntity<Void> requestEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<UserData> result = testRestTemplate
                .exchange("/admin/get-user-data/{username}", HttpMethod.GET, requestEntity, UserData.class, Map.of("username", "user-name-1"));

        assertThat(result.getStatusCode().value()).isEqualTo(200);
        UserData actualEntity = result.getBody();
        assertThat(actualEntity).isNotNull();
        assertThat(actualEntity.getFirstName()).isEqualTo("userFirstName");
        assertThat(actualEntity.getLastName()).isEqualTo("userLastName");
    }

    @Test
    public void test_getUserData_returns400_forNonExistentUsername_forAdminUser() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth("admin-token");
        HttpEntity<Void> requestEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<Void> result = testRestTemplate
                .exchange("/admin/get-user-data/{username}", HttpMethod.GET, requestEntity, Void.class, Map.of("username", "non-existent-user-name"));

        assertThat(result.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    public void test_getUserData_returns403_forNonAdminUser() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth("user-token");
        HttpEntity<Void> requestEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<Void> result = testRestTemplate
                .exchange("/admin/get-user-data/{username}", HttpMethod.GET, requestEntity, Void.class, Map.of("username", "user-name-1"));

        assertThat(result.getStatusCode().value()).isEqualTo(403);
    }

    @Test
    public void test_getUserData_returns401_forInvalidAuthentication() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth("invalid-token");
        HttpEntity<Void> requestEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<Void> result = testRestTemplate
                .exchange("/admin/get-user-data/{username}", HttpMethod.GET, requestEntity, Void.class, Map.of("username", "user-name-1"));

        assertThat(result.getStatusCode().value()).isEqualTo(401);
    }

    @Test
    public void test_getUserData_returns401_forMissingAuthentication() {
        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<Void> result = testRestTemplate
                .exchange("/admin/get-user-data/{username}", HttpMethod.GET, requestEntity, Void.class, Map.of("username", "user-name-1"));

        assertThat(result.getStatusCode().value()).isEqualTo(401);
    }
}
