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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.config.handler
 * @fileName : Http401Handler
 * @date : 2025-04-06
 */

@Slf4j
@RequiredArgsConstructor
public class Http401Handler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        log.error("[인증오류] 로그인이 필요합니다." );

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("401")
                .message("로그인이 필요합니다")
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding(UTF_8.name());

        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
