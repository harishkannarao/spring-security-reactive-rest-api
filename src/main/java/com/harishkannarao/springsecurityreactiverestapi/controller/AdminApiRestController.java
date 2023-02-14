package com.harishkannarao.springsecurityreactiverestapi.controller;

import com.harishkannarao.springsecurityreactiverestapi.domain.UserData;
import com.harishkannarao.springsecurityreactiverestapi.security.resolver.UserDataResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = {"/admin"})
public class AdminApiRestController {

    private final UserDataResolver userDataResolver;

    @Autowired
    public AdminApiRestController(UserDataResolver userDataResolver) {
        this.userDataResolver = userDataResolver;
    }

    @GetMapping(value = {"/get-user-data/{username}"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Mono<UserData> getUserData(ServerWebExchange serverWebExchange,
                                      @PathVariable("username") String username) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
        Mono<UserData> userData = userDataResolver.resolve(username);
        return userData.map(value -> {
            serverWebExchange.getResponse().setStatusCode(HttpStatus.OK);
            return value;
        });
    }
}
