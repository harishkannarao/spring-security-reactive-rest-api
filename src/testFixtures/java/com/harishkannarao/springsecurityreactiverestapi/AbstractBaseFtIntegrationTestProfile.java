package com.harishkannarao.springsecurityreactiverestapi;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = {"integration-test", "ft-integration-test"})
public abstract class AbstractBaseFtIntegrationTestProfile extends AbstractBaseIntegrationTest {
}
