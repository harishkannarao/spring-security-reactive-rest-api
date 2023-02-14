package com.harishkannarao.springsecurityreactiverestapi.controller;

import com.harishkannarao.springsecurityreactiverestapi.domain.UserData;
import com.harishkannarao.springsecurityreactiverestapi.fixtures.UserDataFixtures;
import com.harishkannarao.springsecurityreactiverestapi.fixtures.UserDetailsFixtures;
import com.harishkannarao.springsecurityreactiverestapi.security.helper.AuthenticationHelper;
import com.harishkannarao.springsecurityreactiverestapi.security.resolver.UserDataResolver;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class UserDataRestControllerTest {

    private final UserDataResolver mockUserDataResolver = Mockito.mock(UserDataResolver.class);
    private final AuthenticationHelper mockAuthenticationHelper = Mockito.mock(AuthenticationHelper.class);
    private final UserDataRestController underTest = new UserDataRestController(mockUserDataResolver, mockAuthenticationHelper);

    @Test
    public void getUserData_returnsUserData() {
        UserDetails userDetails = UserDetailsFixtures.createAnUser();

        ServerWebExchange serverWebExchange = MockServerWebExchange.builder(
                MockServerHttpRequest.get("/user-data"))
                .build();

        UserData userData = UserDataFixtures.anUserData();
        when(mockAuthenticationHelper.getCurrentUsername()).thenReturn(Mono.just(userDetails.getUsername()));
        when(mockUserDataResolver.resolve(userDetails.getUsername())).thenReturn(Mono.just(userData));

        Mono<UserData> result = underTest.getUserData(serverWebExchange);

        assertThat(result.block()).isEqualTo(userData);
        assertThat(serverWebExchange.getResponse().getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    public void getUserData_returnsBadRequest_forNonExistentUserData() {
        UserDetails userDetails = UserDetailsFixtures.createAnUser();

        ServerWebExchange serverWebExchange = MockServerWebExchange.builder(
                        MockServerHttpRequest.get("/user-data"))
                .build();

        when(mockAuthenticationHelper.getCurrentUsername()).thenReturn(Mono.just(userDetails.getUsername()));
        when(mockUserDataResolver.resolve(userDetails.getUsername())).thenReturn(Mono.empty());

        Mono<UserData> result = underTest.getUserData(serverWebExchange);
        assertThat(result.blockOptional()).isEmpty();
        assertThat(serverWebExchange.getResponse().getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
