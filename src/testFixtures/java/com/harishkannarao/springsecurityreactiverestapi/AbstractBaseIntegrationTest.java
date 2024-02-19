package com.harishkannarao.springsecurityreactiverestapi;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.util.TestSocketUtils;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
abstract class AbstractBaseIntegrationTest {

    @DynamicPropertySource
    static void registerTestProperties(DynamicPropertyRegistry registry) {
        final int RANDOM_SERVER_PORT = TestSocketUtils.findAvailableTcpPort();
        registry.add("server.port", () -> String.valueOf(RANDOM_SERVER_PORT));
    }
}
