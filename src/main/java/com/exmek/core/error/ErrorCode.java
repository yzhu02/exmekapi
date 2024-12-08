package com.exmek.core.error;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

	String ERR_CODE_UNAUTHORIZED					= String.valueOf(HttpStatus.UNAUTHORIZED.value());
	
	String ERR_CODE_FORBIDDEN						= String.valueOf(HttpStatus.FORBIDDEN.value());
	
	String ERR_CODE_UNCAUGHT_EXCEPTION				= String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
	
	String ERR_CODE_INQUIRY_MISSING_REQUEST_PAYLOAD	= String.valueOf(HttpStatus.BAD_REQUEST.value()) + ".INQUIRY.MISSING_REQUEST_PAYLOAD";
}
