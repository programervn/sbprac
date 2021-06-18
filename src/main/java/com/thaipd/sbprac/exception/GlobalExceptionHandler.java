package com.thaipd.sbprac.exception;

import com.thaipd.sbprac.common.AppConstants;
import com.thaipd.sbprac.model.ApiErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenException(UsernameNotFoundException ex) {
        logger.warn("**********Authentication exception due to UsernameNotFoundException");
        ApiErrorResponse response =
                new ApiErrorResponse(AppConstants.ERR_AUTHENTICATE_USER,
                        ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenException(AuthenticationException ex) {
        logger.warn("**********Authentication exception handling");
        ApiErrorResponse response =
                new ApiErrorResponse(AppConstants.ERR_AUTHENTICATE_OTHER,
                        ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiErrorResponse resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ApiErrorResponse response =
                new ApiErrorResponse(AppConstants.ERR_RESOURCE_NOTFOUND,
                        ex.getMessage());
        return response;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(Exception ex) {
        logger.warn("**********General exception handling");
        ApiErrorResponse response =
                new ApiErrorResponse(AppConstants.ERR_EXCEPTION,
                        ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
