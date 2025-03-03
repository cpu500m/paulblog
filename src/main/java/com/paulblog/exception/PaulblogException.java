package com.paulblog.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.exception
 * @fileName : PaulException
 * @date : 2025-03-03
 */
@Getter
public abstract class PaulblogException extends RuntimeException{

    private final Map<String,String> validation = new HashMap<>();

    public PaulblogException() {
    }

    public PaulblogException(String message) {
        super(message);
    }

    public PaulblogException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
