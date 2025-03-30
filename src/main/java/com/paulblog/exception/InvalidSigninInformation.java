package com.paulblog.exception;

import org.springframework.http.HttpStatus;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.exception
 * @fileName : InvalidSigninInformation
 * @date : 2025-03-17
 */
public class InvalidSigninInformation extends PaulblogException{

    private static final String MESSAGE = "아이디/비밀번호가 올바르지 않습니다.";

    public InvalidSigninInformation() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }
}
