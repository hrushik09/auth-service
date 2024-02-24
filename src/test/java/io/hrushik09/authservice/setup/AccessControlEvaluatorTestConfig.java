package io.hrushik09.authservice.setup;

import io.hrushik09.authservice.config.AccessControlEvaluator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccessControlEvaluatorTestConfig {
    @Bean
    AccessControlEvaluator accessControlEvaluator() {
        return new AccessControlEvaluator();
    }
}
