package com.exmek.core.exception;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ApiExceptionHandler {

	HttpStatus resolveHttpStatus(String errCode) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		if (StringUtils.isEmpty(errCode)) {
			return status;
		}
		int inx = errCode.indexOf('.');
		try {
			if (inx > 0) {
				return HttpStatus.resolve(Integer.valueOf(errCode.substring(0, inx)));
			} else if (inx < 0) {
				return HttpStatus.resolve(Integer.valueOf(errCode));
			} else {
				return status;
			}
		} catch (Exception ex) {
			return status;
		}
	}

	@ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
    		ValidationException ex, WebRequest request) {
        ErrorResponse error = ErrorResponse.builder()
        		.code(ex.getCode())
        		.message(ex.getMessage())
        		.build();
        return ResponseEntity
        		.status(resolveHttpStatus(ex.getCode()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }

    @ExceptionHandler(BizRuntimeException.class)
    public ResponseEntity<ErrorResponse> handleBizRuntimeException(
    		BizRuntimeException ex, WebRequest request) {
    	ErrorResponse error = ErrorResponse.builder()
        		.code(ex.getCode())
        		.message(ex.getMessage())
        		.build();
        return ResponseEntity
        		.status(resolveHttpStatus(ex.getCode()))
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
