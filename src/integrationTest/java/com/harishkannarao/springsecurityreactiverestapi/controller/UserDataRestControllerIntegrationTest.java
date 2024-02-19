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

import static org.assertj.core.api.Assertions.assertThat;

public class UserDataRestControllerIntegrationTest extends AbstractBaseIntegrationTestProfile {

    private final TestRestTemplate testRestTemplate;

    @Autowired
    public UserDataRestControllerIntegrationTest(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    @Test
    public void test_getUserData() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth("user-token");
        HttpEntity<Void> requestEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<UserData> result = testRestTemplate
                .exchange("/user-data", HttpMethod.GET, requestEntity, UserData.class);

        assertThat(result.getStatusCode().value()).isEqualTo(200);
        UserData actualEntity = result.getBody();
        assertThat(actualEntity).isNotNull();
        assertThat(actualEntity.getFirstName()).isEqualTo("userFirstName");
        assertThat(actualEntity.getLastName()).isEqualTo("userLastName");
    }
}
