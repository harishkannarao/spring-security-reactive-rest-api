package com.harishkannarao.springsecurityreactiverestapi.controller;

import com.harishkannarao.springsecurityreactiverestapi.domain.UserData;
import com.harishkannarao.springsecurityreactiverestapi.security.helper.AuthenticationHelper;
import com.harishkannarao.springsecurityreactiverestapi.security.resolver.UserDataResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = {"/beta/user-data"})
@ConditionalOnProperty(name = "feature.beta.enabled", havingValue = "true")
public class UserDataBetaRestController {

    private final UserDataResolver userDataResolver;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public UserDataBetaRestController(UserDataResolver userDataResolver, AuthenticationHelper authenticationHelper) {
        this.userDataResolver = userDataResolver;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public Mono<UserData> getUserData() {
        Mono<UserData> userData = authenticationHelper.getCurrentUsername()
                .flatMap(userDataResolver::resolve);
        return userData.switchIfEmpty(Mono.error(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));
    }
}
