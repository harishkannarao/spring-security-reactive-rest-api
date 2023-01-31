package com.harishkannarao.springsecurityreactiverestapi.fixtures;

import com.harishkannarao.springsecurityreactiverestapi.domain.UserData;

public class UserDataFixtures {
    public static UserData anUserData() {
        return UserData.builder()
                .firstName("some-first-name")
                .lastName("some-last-name")
                .build();
    }
}
