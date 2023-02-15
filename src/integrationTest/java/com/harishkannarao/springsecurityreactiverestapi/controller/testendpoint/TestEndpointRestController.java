package com.harishkannarao.springsecurityreactiverestapi.controller.testendpoint;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = {"/test-endpoint"})
public class TestEndpointRestController {

    @GetMapping
    public Mono<Void> success(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.NO_CONTENT);
        return Mono.empty();
    }
}
