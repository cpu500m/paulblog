package com.paulblog.crypto;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.crypto
 * @fileName : PasswordEncoder
 * @date : 2025-03-30
 */
public interface PasswordEncoder {
    String encrypt(String password);
    boolean matches(String rawPassword, String encryptedPassword);
}
