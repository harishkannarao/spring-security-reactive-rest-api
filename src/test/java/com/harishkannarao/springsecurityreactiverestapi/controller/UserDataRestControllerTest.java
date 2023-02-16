package com.harishkannarao.springsecurityreactiverestapi.controller;

import com.harishkannarao.springsecurityreactiverestapi.domain.UserData;
import com.harishkannarao.springsecurityreactiverestapi.fixtures.UserDataFixtures;
import com.harishkannarao.springsecurityreactiverestapi.fixtures.UserDetailsFixtures;
import com.harishkannarao.springsecurityreactiverestapi.security.helper.AuthenticationHelper;
import com.harishkannarao.springsecurityreactiverestapi.security.resolver.UserDataResolver;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UserDataRestControllerTest {

    private final UserDataResolver mockUserDataResolver = Mockito.mock(UserDataResolver.class);
    private final AuthenticationHelper mockAuthenticationHelper = Mockito.mock(AuthenticationHelper.class);
    private final UserDataRestController underTest = new UserDataRestController(mockUserDataResolver, mockAuthenticationHelper);

    @Test
    public void getUserData_returnsUserData() {
        UserDetails userDetails = UserDetailsFixtures.createAnUser();

        UserData userData = UserDataFixtures.anUserData();
        when(mockAuthenticationHelper.getCurrentUsername()).thenReturn(Mono.just(userDetails.getUsername()));
        when(mockUserDataResolver.resolve(userDetails.getUsername())).thenReturn(Mono.just(userData));

        Mono<UserData> result = underTest.getUserData();

        assertThat(result.block()).isEqualTo(userData);
    }


    @Test
    public void getUserData_returnsBadRequest_forNonExistentUserData() {
        UserDetails userDetails = UserDetailsFixtures.createAnUser();

        when(mockAuthenticationHelper.getCurrentUsername()).thenReturn(Mono.just(userDetails.getUsername()));
        when(mockUserDataResolver.resolve(userDetails.getUsername())).thenReturn(Mono.empty());

        Mono<UserData> result = underTest.getUserData();
        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, result::block);
        assertThat(responseStatusException.getStatusCode().value()).isEqualTo(400);
    }

}
