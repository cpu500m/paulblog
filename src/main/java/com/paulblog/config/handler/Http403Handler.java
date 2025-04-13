package com.paulblog.config.handler;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulblog.httpresponsedto.common.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.config.handler
 * @fileName : Http403Handler
 * @date : 2025-04-06
 */

@RequiredArgsConstructor
@Slf4j
public class Http403Handler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("[인증오류] 403");

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("403")
                .message("접근할 수 없습니다.")
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding(UTF_8.name());

        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
