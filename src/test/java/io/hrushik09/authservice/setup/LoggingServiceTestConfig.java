package io.hrushik09.authservice.setup;

import io.hrushik09.authservice.config.log.LoggingService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class LoggingServiceTestConfig {
    @Bean
    public LoggingService loggingService() {
        return new LoggingService();
    }
}
