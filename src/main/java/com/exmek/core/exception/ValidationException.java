package com.exmek.core.exception;

public class ValidationException extends BizRuntimeException {

	private static final long serialVersionUID = 1L;

    public ValidationException() {
        super();
    }

    public ValidationException(String message, String code) {
        super(message, code);
    }

    public ValidationException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }

}
