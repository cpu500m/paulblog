package com.paulblog.crypto;

/**
 * @author : paulkim
 * @description : Spring security 쓰면서 deprecated
 * @packageName : com.paulblog.crypto
 * @fileName : PasswordEncoder
 * @date : 2025-03-30
 */
public interface PasswordEncoder {
    String encrypt(String password);
    boolean matches(String rawPassword, String encryptedPassword);
}
