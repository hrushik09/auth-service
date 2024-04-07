package io.hrushik09.authservice.config.log;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class NoRequestBodyInterceptor implements HandlerInterceptor {
    private final LoggingService loggingService;

    public NoRequestBodyInterceptor(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals(HttpMethod.GET.name()) || request.getMethod().equals(HttpMethod.DELETE.name()) || request.getMethod().equals(HttpMethod.PUT.name())) {
            loggingService.displayReq(request, null);
        }
        return true;
    }
}
