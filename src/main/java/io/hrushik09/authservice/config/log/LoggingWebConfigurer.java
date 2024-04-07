package io.hrushik09.authservice.config.log;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class LoggingWebConfigurer implements WebMvcConfigurer {
    private final NoRequestBodyInterceptor noRequestBodyInterceptor;

    public LoggingWebConfigurer(NoRequestBodyInterceptor noRequestBodyInterceptor) {
        this.noRequestBodyInterceptor = noRequestBodyInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(noRequestBodyInterceptor);
    }
}
