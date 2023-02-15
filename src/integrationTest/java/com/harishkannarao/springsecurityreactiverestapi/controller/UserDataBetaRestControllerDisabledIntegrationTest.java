package com.harishkannarao.springsecurityreactiverestapi.controller;

import com.harishkannarao.springsecurityreactiverestapi.AbstractBaseIntegrationTestProfile;
import com.harishkannarao.springsecurityreactiverestapi.domain.UserData;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDataBetaRestControllerDisabledIntegrationTest extends AbstractBaseIntegrationTestProfile {

    @Test
    public void test_apiDisabled_andReturns403_forAuthenticatedRequest() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth("user-token");
        HttpEntity<Void> requestEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<UserData> result = testRestTemplate()
                .exchange("/beta/user-data", HttpMethod.GET, requestEntity, UserData.class);

        assertThat(result.getStatusCode().value()).isEqualTo(403);
    }

    @Test
    public void test_apiDisabled_andReturns401_forUnAuthenticatedRequest() {
        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<UserData> result = testRestTemplate()
                .exchange("/beta/user-data", HttpMethod.GET, requestEntity, UserData.class);

        assertThat(result.getStatusCode().value()).isEqualTo(401);
    }
}
