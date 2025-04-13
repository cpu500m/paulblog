package com.paulblog.config;

import com.paulblog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.config
 * @fileName : MethoSecurityConfig
 * @date : 2025-04-06
 */
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class MethodSecurityConfig {

    private final PostRepository postRepository;

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setPermissionEvaluator(new PaulBlogPermissionEvaluator(postRepository));
        return handler;
    }
}
