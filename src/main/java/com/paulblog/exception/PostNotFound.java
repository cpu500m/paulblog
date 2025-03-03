package com.paulblog.exception;

import org.springframework.http.HttpStatus;

/**
 * @author : paulkim
 * @description : status -> 404
 * @packageName : com.paulblog.exception
 * @fileName : PostNotFound
 * @date : 2025-03-02
 */
public class PostNotFound extends PaulblogException {

    private static final String MESSAGE = "존재하지 않는 글입니다";


    public PostNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }

    public PostNotFound(Throwable cause) {
        super(MESSAGE, cause);
    }
}
