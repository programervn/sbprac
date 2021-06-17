package com.thaipd.sbprac.controller;

import com.thaipd.sbprac.model.ApiErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenException(AuthenticationException ex) {
        logger.warn("**********Authentication exception handling");
        ApiErrorResponse response =
                new ApiErrorResponse("error-0002",
                        ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(Exception ex) {
        logger.warn("**********General exception handling");
        ApiErrorResponse response =
                new ApiErrorResponse("error-0001",
                        ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
