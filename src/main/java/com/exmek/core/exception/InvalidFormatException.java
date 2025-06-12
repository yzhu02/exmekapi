package com.exmek.core.exception;

public class InvalidFormatException extends BizRuntimeException {

	private static final long serialVersionUID = 1L;

    public InvalidFormatException() {
        super();
    }

    public InvalidFormatException(String message) {
        super(message);
    }
    
    public InvalidFormatException(String message, String code) {
        super(message, code);
    }

    public InvalidFormatException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }

}
