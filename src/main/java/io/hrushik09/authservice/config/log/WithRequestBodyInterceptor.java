package io.hrushik09.authservice.config.log;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

@RestControllerAdvice
public class WithRequestBodyInterceptor extends RequestBodyAdviceAdapter {
    private final LoggingService loggingService;
    private final HttpServletRequest request;

    public WithRequestBodyInterceptor(LoggingService loggingService, HttpServletRequest request) {
        this.loggingService = loggingService;
        this.request = request;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        loggingService.displayReq(request, body);
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}
