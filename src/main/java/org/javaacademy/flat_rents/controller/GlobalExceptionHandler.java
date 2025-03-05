package org.javaacademy.flat_rents.controller;

import lombok.extern.slf4j.Slf4j;
import org.javaacademy.flat_rents.dto.ErrorResponse;
import org.javaacademy.flat_rents.exception.DateConflictException;
import org.javaacademy.flat_rents.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            NotFoundException.class,
    })
    public ResponseEntity<ErrorResponse> handleNotFoundException(RuntimeException e) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({
            DateConflictException.class
    })
    public ResponseEntity<ErrorResponse> handleDateConflictException(RuntimeException e) {
        return buildErrorResponse(HttpStatus.CONFLICT, e.getMessage());
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(status.value(), status.name(), message));
    }

}
