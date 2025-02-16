package com.mohit.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mohit.backend.auth.exception.ForgetPasswordException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MovieNotFoundException.class)
    public ProblemDetail handleMovieNotFound(MovieNotFoundException mc){
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, mc.getMessage());
    }
    @ExceptionHandler(ForgetPasswordException.class)
    public ProblemDetail handleForgetPasswordException(ForgetPasswordException fc){
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, fc.getMessage());
    }
}
