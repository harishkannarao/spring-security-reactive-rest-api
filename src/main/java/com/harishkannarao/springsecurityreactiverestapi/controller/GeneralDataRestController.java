package com.harishkannarao.springsecurityreactiverestapi.controller;

import com.harishkannarao.springsecurityreactiverestapi.domain.GeneralData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = {"/general-data"})
public class GeneralDataRestController {

    @GetMapping
    public ResponseEntity<Mono<GeneralData>> getGeneralData() {
        GeneralData entity = GeneralData.builder()
                .message("Welcome !!!")
                .build();
        return ResponseEntity.ok().body(Mono.just(entity));
    }
}
