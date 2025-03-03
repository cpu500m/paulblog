package com.paulblog.controller;

import com.paulblog.exception.InvalidRequest;
import com.paulblog.exception.PaulblogException;
import com.paulblog.httpresponsedto.common.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.controller
 * @fileName : ExceptionController
 * @date : 2025-02-16
 */

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {

        ErrorResponse response = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();

        for (FieldError fieldError : e.getFieldErrors()) {
            response.addValidation(fieldError.getField(),fieldError.getDefaultMessage());
        }
        return response;
    }

    @ExceptionHandler(PaulblogException.class)
    public ResponseEntity<ErrorResponse> postNotFoundHandler(PaulblogException e) {
        int statusCode = e.getStatusCode();

        ErrorResponse response = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        return ResponseEntity.status(statusCode)
                .body(response);
    }
}
