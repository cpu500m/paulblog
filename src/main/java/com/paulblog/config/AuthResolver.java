package com.paulblog.config;

import com.paulblog.config.data.UserSession;
import com.paulblog.exception.Unauthorized;
import com.paulblog.repository.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.config
 * @fileName : AuthResolver
 * @date : 2025-03-16
 */

@RequiredArgsConstructor
@Slf4j
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;
    private final AppConfig appConfig;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info(">>>{}", appConfig.toString());


        String jws = webRequest.getHeader("Authorization");

        if(jws == null || jws.equals("")) {
            throw new Unauthorized();
        }

        byte[] decodedKey = appConfig.getJwtKey();
        SecretKey originalKey = new SecretKeySpec(decodedKey, "HmacSHA256");

        try {

            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(originalKey)
                    .build()
                    .parseSignedClaims(jws);

            String userId = claims.getPayload().getSubject();
            log.info(">>>>>>{}" , claims);

            return new UserSession(Long.parseLong(userId));
        } catch (JwtException e) {
            throw new Unauthorized();
        }
    }
}