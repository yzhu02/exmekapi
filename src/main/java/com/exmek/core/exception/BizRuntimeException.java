package com.exmek.core.exception;

public class BizRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String code;

	public BizRuntimeException() {
        super();
    }

	public BizRuntimeException(String message) {
        super(message);
    }
    
    public BizRuntimeException(String message, String code) {
        super(message);
        this.code = code;
    }

    public BizRuntimeException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }

	public String getCode() {
		return code;
	}

}
