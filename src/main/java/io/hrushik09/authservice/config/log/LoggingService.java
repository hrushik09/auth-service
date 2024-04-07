package io.hrushik09.authservice.config.log;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LoggingService {
    Logger logger = LoggerFactory.getLogger(LoggingService.class);

    public void displayReq(HttpServletRequest request, Object body) {
        Map<String, String[]> parameters = getParameters(request);
        StringBuilder reqMessage = new StringBuilder();
        reqMessage.append("REQUEST:");
        reqMessage.append("method = [").append(request.getMethod()).append("], ");
        reqMessage.append("path = [").append(request.getRequestURI()).append("], ");
        reqMessage.append("query = [").append(request.getQueryString()).append("], ");
        if (!parameters.isEmpty()) {
            reqMessage.append("request parameters = [").append(parameters).append("], ");
        }
        if (!Objects.nonNull(body)) {
            reqMessage.append("request body = [").append(body).append("], ");
        }
        logger.info("{}", reqMessage);
    }

    private Map<String, String[]> getParameters(HttpServletRequest request) {
        return request.getParameterMap().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void displayResp(HttpServletRequest request, HttpServletResponse response, Object body) {
        Map<String, String> headers = getHeaders(response);
        StringBuilder respMessage = new StringBuilder();
        respMessage.append("RESPONSE:");
        respMessage.append("method = [").append(request.getMethod()).append("], ");
        respMessage.append("path = [").append(request.getRequestURI()).append("], ");
        respMessage.append("query = [").append(request.getQueryString()).append("], ");
        if (!headers.isEmpty()) {
            respMessage.append("response headers = [").append(headers).append("], ");
        }
        respMessage.append("response body = [").append(body).append("], ");
        logger.info("{}", respMessage);
    }

    private Map<String, String> getHeaders(HttpServletResponse response) {
        return response.getHeaderNames().stream()
                .collect(Collectors.toMap(name -> name, response::getHeader, (existing, replacement) -> existing));
    }
}
