package com.paulblog.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

/**
 * @author : paulkim
 * @description : 이런식으로 쓸수있음 . 테스트 메서드에 @CustomWithMockUser 달면 메서드 실행전 security context에 주입.
 * @packageName : com.paulblog.annotation
 * @fileName : CustomWithMockUser
 * @date : 2025-04-06
 */

// @WithSecurityContext 을 통해 회원가입을 진행하고 Spring security context안에 미리 넣어둠
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = PaulBlogMockSecurityContext.class)
public @interface PaulBlogMockUser {

    String name() default "김바울";
    String email() default "paul108203@naver.com";
    String password() default "1234";
}
