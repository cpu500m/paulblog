package com.paulblog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author : paulkim
 * @description : status -> 400
 * @packageName : com.paulblog.exception
 * @fileName : InvalidRequest
 * @date : 2025-03-02
 */
@Getter
public class InvalidRequest extends PaulblogException {

    private static String MESSAGE ="잘못된 요청입니다.";

    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
