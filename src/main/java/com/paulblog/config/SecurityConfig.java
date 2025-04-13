package com.paulblog.config;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulblog.config.filter.EmailPasswordAuthFilter;
import com.paulblog.config.handler.Http401Handler;
import com.paulblog.config.handler.Http403Handler;
import com.paulblog.config.handler.LoginFailHandler;
import com.paulblog.config.handler.LoginSuccessHandler;
import com.paulblog.domain.User;
import com.paulblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.config
 * @fileName : SecurityConfig
 * @date : 2025-04-02
 */

@Configuration
@EnableWebSecurity(debug=true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    // security 필터 제외할 경로
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/favicon.ico")
                .requestMatchers("/error")
                .requestMatchers(toH2Console());
    }

    // security 로그인 관련 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/auth/login").permitAll()
//                        .requestMatchers("/auth/signup").permitAll()
//                        .requestMatchers("/user").hasAnyRole("USER","ADMIN")
//                        .requestMatchers("/admin")
//                        .access(new WebExpressionAuthorizationManager("hasRole('ADMIN') AND hasAuthority('WRITE')"))
                        .anyRequest().permitAll()
                )
                /* 폼 로그인 시 사용 */
//                .formLogin(requests -> requests
//                        .loginPage("/auth/login")  // 로그인 페이지로 사용할 주소
//                        .loginProcessingUrl("/auth/login") // POST로 받아서 값을 검증하는 주소
//                        .usernameParameter("username")
//                        .passwordParameter("password")
//                        .defaultSuccessUrl("/")
//                        .failureHandler(new LoginFailHandler(objectMapper))
//                        .securityContextRepository(new HttpSessionSecurityContextRepository())
//                )
                .addFilterBefore(emailPasswordAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(e -> {
                    e.accessDeniedHandler(new Http403Handler(objectMapper));
                    e.authenticationEntryPoint(new Http401Handler(objectMapper));
                })
                .rememberMe(rm -> rm.rememberMeParameter("remember")
                        .alwaysRemember(false)
                        .tokenValiditySeconds(2592000) // 30일 ( 한달 )
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService(userRepository));
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public EmailPasswordAuthFilter emailPasswordAuthFilter() {
        EmailPasswordAuthFilter filter = new EmailPasswordAuthFilter("/auth/login", objectMapper);
        filter.setAuthenticationManager(authenticationManager());
        // 로그인 성공 시 루트로 이동
//        filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/"));
        // 로그인 성공하면 정보 출력하도록 변경
        filter.setAuthenticationSuccessHandler(new LoginSuccessHandler());
        filter.setAuthenticationFailureHandler(new LoginFailHandler(objectMapper));
        // 아래 코드가 없으면 세션 발급이 안됨.
        filter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());

        // todo remeberme를 경우에 따라 분기처리 해보기 (항상 한달 고정 세션이 아니라)
        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        rememberMeServices.setAlwaysRemember(true);
        rememberMeServices.setValiditySeconds(3600 * 24 * 30);
        filter.setRememberMeServices(rememberMeServices);
        return filter;
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(
                            () -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));

            return new UserPrincipal(user);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SCryptPasswordEncoder(
                16,
                8,
                1,
                32,
                16);
    }
}
