package com.paulblog.annotation;

import com.paulblog.config.UserPrincipal;
import com.paulblog.domain.User;
import com.paulblog.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.annotation
 * @fileName : MockUserFctory
 * @date : 2025-04-06
 */

@RequiredArgsConstructor
public class PaulBlogMockSecurityContext implements WithSecurityContextFactory<PaulBlogMockUser> {

    private final UserRepository userRepository;

    @Override
    public SecurityContext createSecurityContext(PaulBlogMockUser annotation) {
        User user = User.builder()
                .email(annotation.email())
                .name(annotation.name())
                .password(annotation.password())
                .build();

        userRepository.save(user);

        UserPrincipal principal = new UserPrincipal(user);

        SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_ADMIN");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                principal,
                user.getPassword(),
                List.of(role));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);

        return context;
    }
}