package io.hrushik09.authservice.setup;

import io.hrushik09.authservice.config.security.AccessControlEvaluator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class AccessControlEvaluatorTestConfig {
    @Bean
    AccessControlEvaluator accessControlEvaluator() {
        return new AccessControlEvaluator();
    }
}
