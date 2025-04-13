package com.paulblog.controller;

import com.paulblog.config.AppConfig;
import com.paulblog.httprequestdto.Signup;
import com.paulblog.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : paulkim
 * @description : Spring security 쓰면서 deprecated
 * @packageName : com.paulblog.controller
 * @fileName : UserController
 * @date : 2025-03-17
 */

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final AppConfig appConfig;

    @PostMapping("/auth/signup")
    public void signup(@RequestBody Signup signup) {
        authService.signup(signup);
    }
}