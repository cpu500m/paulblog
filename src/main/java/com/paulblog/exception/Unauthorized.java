package com.paulblog.exception;

import org.springframework.http.HttpStatus;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.exception
 * @fileName : Unauthorized
 * @date : 2025-03-16
 */
public class Unauthorized extends PaulblogException {
    private static String MESSAGE ="인증이 필요합니다.";

    public Unauthorized() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }

    public Unauthorized(Throwable cause) {
        super(MESSAGE, cause);
    }
}
