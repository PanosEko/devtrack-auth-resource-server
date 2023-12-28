package com.panoseko.devtrack.advice;


import com.panoseko.devtrack.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    // RequestBody validation errors are handled here
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setErrorMessage("Invalid request body.");
        errorResponse.setTimestamp(LocalDateTime.now());
        if (!errorMap.isEmpty()) {
            errorResponse.setErrorDetails(errorMap);
        }
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ImageNotFoundException.class)
    public ErrorResponse handleMissingImage(ImageNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND);
        errorResponse.setTimestamp(LocalDateTime.now());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ImageProcessingException.class)
    public ErrorResponse handleImageProcessingException(ImageProcessingException ex) {
        log.error(ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setTimestamp(LocalDateTime.now());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TaskNotFoundException.class)
    public ErrorResponse handleMissingTask(TaskNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND);
        errorResponse.setTimestamp(LocalDateTime.now());
        return errorResponse;
    }

    // Missing RequestParam errors are handled here
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorResponse handleInvalidRequestParameter(MissingServletRequestParameterException  ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestCookieException.class)
    public ErrorResponse handleMissingRequestCookie(MissingRequestCookieException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        return errorResponse;
    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidJwtException.class)
    public ErrorResponse handleInvalidJwt(InvalidJwtException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED);
        errorResponse.setTimestamp(LocalDateTime.now());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ErrorResponse handleAuthenticationException(AuthenticationException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setErrorMessage("Invalid password.");
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED);
        errorResponse.setTimestamp(LocalDateTime.now());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ErrorResponse handleUsernameNotFound(UsernameNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED);
        errorResponse.setTimestamp(LocalDateTime.now());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UsernameAlreadyTakenException.class)
    public ErrorResponse handleUsernameTaken(UsernameAlreadyTakenException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(HttpStatus.CONFLICT.value());
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.CONFLICT);
        errorResponse.setTimestamp(LocalDateTime.now());
        return errorResponse;
    }
}

