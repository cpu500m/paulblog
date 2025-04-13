package com.paulblog.exception;

import org.springframework.http.HttpStatus;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.exception
 * @fileName : UserNotFound
 * @date : 2025-04-06
 */
public class UserNotFound extends PaulblogException {

    private static final String MESSAGE = "존재하지 않는 사용자입니다.";


    public UserNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }

    public UserNotFound(Throwable cause) {
        super(MESSAGE, cause);
    }
}
