package com.paulblog.crypto;

import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.crypto
 * @fileName : PasswordEncoder
 * @date : 2025-03-30
 */

@Profile("default")
@Component
public class ScryptPasswordEncoder implements PasswordEncoder {
    private final SCryptPasswordEncoder encoder = new SCryptPasswordEncoder(
            16,
            8,
            1,
            32,
            16);

    public String encrypt(String rawPassword){
        return encoder.encode(rawPassword);
    }

    public boolean matches(String rawPassword, String encodedPassword){
        return encoder.matches(rawPassword, encodedPassword);
    }
}