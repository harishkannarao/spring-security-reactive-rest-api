package com.harishkannarao.springsecurityreactiverestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harishkannarao.springsecurityreactiverestapi.domain.UserData;
import com.harishkannarao.springsecurityreactiverestapi.security.helper.AuthenticationHelper;
import com.harishkannarao.springsecurityreactiverestapi.security.resolver.UserDataResolver;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser;

public class UserDataBetaRestControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    private final UserDataResolver userDataResolver = mock();
    private final AuthenticationHelper authenticationHelper = mock();
    private final UserDataBetaRestController userDataBetaRestController = new UserDataBetaRestController(
            userDataResolver,
            authenticationHelper);
    private final WebTestClient webTestClient = WebTestClient
            .bindToController(userDataBetaRestController)
            .build();

    @Test
    public void getUserData_returns_success_response() throws Exception {
        String userName = "user-name" + UUID.randomUUID();
        String requestId = UUID.randomUUID().toString();
        when(authenticationHelper.getCurrentUsername(
                assertArg(serverWebExchange ->
                        assertThat(serverWebExchange.getRequest().getHeaders().getFirst("request-id"))
                                .isEqualTo(requestId)))
        ).thenReturn(Mono.just(userName));
        UserData expectedUserData = UserData.builder()
                .firstName("first-" + UUID.randomUUID())
                .lastName("last-" + UUID.randomUUID())
                .build();
        when(userDataResolver.resolve(userName))
                .thenReturn(Mono.just(expectedUserData));


        FluxExchangeResult<String> result = webTestClient
                .mutateWith(mockUser())
                .get()
                .uri("/beta/user-data")
                .header("request-id", requestId)
                .exchange()
                .returnResult(String.class);

        String responseBody = result.getResponseBody().toStream().collect(Collectors.joining());

        assertThat(result.getStatus().value()).isEqualTo(200);
        UserData userData = objectMapper.readValue(responseBody, UserData.class);
        assertThat(userData)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedUserData);
    }

    @Test
    public void getUserData_returns_bad_request_response() throws Exception {
        String userName = "user-name" + UUID.randomUUID();
        String requestId = UUID.randomUUID().toString();
        when(authenticationHelper.getCurrentUsername(
                assertArg(serverWebExchange ->
                        assertThat(serverWebExchange.getRequest().getHeaders().getFirst("request-id"))
                                .isEqualTo(requestId)))
        ).thenReturn(Mono.just(userName));
        when(userDataResolver.resolve(userName))
                .thenReturn(Mono.empty());
        FluxExchangeResult<String> result = webTestClient
                .get()
                .uri("/beta/user-data")
                .header("request-id", requestId)
                .exchange()
                .returnResult(String.class);
        assertThat(result.getStatus().value()).isEqualTo(400);
    }
}
