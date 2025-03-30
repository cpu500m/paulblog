package com.paulblog.crypto;

import org.springframework.boot.test.context.TestComponent;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.crypto
 * @fileName : PlainPasswordEncoder
 * @date : 2025-03-30
 */
@TestComponent
public class PlainPasswordEncoder implements PasswordEncoder{

    @Override
    public String encrypt(String rawPassword) {
        return rawPassword;
    }

    @Override
    public boolean matches(String rawPassword, String encryptedPassword) {
        return rawPassword.equals(encryptedPassword);
    }
}

