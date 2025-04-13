package com.paulblog.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.config
 * @fileName : WebConfig
 * @date : 2025-03-04
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final AppConfig appConfig;
    //des interceptor를 통한 인증방식을 전부 ArgResolver를 이용하여 처리하도록 변경.
    /*
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .excludePathPatterns("/error", "/favicon.ico");
    }
*/
    //DES Spring security를 사용하도록 변경하면서 ArgResolver 사용할일 X
    /*
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthResolver(sessionRepository,appConfig));
    }
    */
}
