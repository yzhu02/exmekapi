package com.exmek.core.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
    		ValidationException ex, WebRequest request) {
        ErrorResponse error = ErrorResponse.builder()
        		.code(ex.getCode())
        		.message(ex.getMessage())
        		.build();
        return ResponseEntity
        		.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }

    @ExceptionHandler(BizRuntimeException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
    		BizRuntimeException ex, WebRequest request) {
    	ErrorResponse error = ErrorResponse.builder()
        		.code(ex.getCode())
        		.message(ex.getMessage())
        		.build();
        return ResponseEntity
        		.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
    	ErrorResponse error = ErrorResponse.builder()
        		.message(ex.getMessage())
        		.build();
        return ResponseEntity
        		.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }
}
