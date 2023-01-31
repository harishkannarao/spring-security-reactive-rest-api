package com.harishkannarao.springsecurityreactiverestapi.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@Jacksonized
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GeneralData {
    String message;
}
