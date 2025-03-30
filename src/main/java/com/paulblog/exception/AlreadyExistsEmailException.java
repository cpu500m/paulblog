package com.paulblog.exception;

import org.springframework.http.HttpStatus;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.exception
 * @fileName : AlreaadyExistsEmailException
 * @date : 2025-03-30
 */
public class AlreadyExistsEmailException extends PaulblogException {

    private static String MESSAGE = "이미 가입된 이메일입니다.";

    public AlreadyExistsEmailException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
