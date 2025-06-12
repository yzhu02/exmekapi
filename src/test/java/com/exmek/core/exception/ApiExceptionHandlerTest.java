package com.exmek.core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ApiExceptionHandlerTest {

	private ApiExceptionHandler apiExceptionHandler = new ApiExceptionHandler();
	
	@Test
	void testResolveHttpStatus() {
		assertEquals(HttpStatus.FORBIDDEN, apiExceptionHandler.resolveHttpStatus(ErrorCode.ERR_CODE_FORBIDDEN));
		assertEquals(HttpStatus.UNAUTHORIZED, apiExceptionHandler.resolveHttpStatus(ErrorCode.ERR_CODE_UNAUTHORIZED));
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, apiExceptionHandler.resolveHttpStatus(ErrorCode.ERR_CODE_UNCAUGHT_EXCEPTION));
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, apiExceptionHandler.resolveHttpStatus(ErrorCode.ERR_CODE_HEALTHCHECK_DB_QUERY_FAILED));
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, apiExceptionHandler.resolveHttpStatus(ErrorCode.ERR_CODE_HEALTHCHECK_MISSING_MOTOR_CONFIG));
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, apiExceptionHandler.resolveHttpStatus(ErrorCode.ERR_CODE_LINEAR_STEPPER_MOTOR_PERF_CURVE_DATA_MALFORMED));
		assertEquals(HttpStatus.BAD_REQUEST, apiExceptionHandler.resolveHttpStatus(ErrorCode.ERR_CODE_INQUIRY_MISSING_REQUEST_PAYLOAD));
		assertEquals(HttpStatus.BAD_REQUEST, apiExceptionHandler.resolveHttpStatus(ErrorCode.ERR_CODE_REQUIRE_BOTH_OR_NONE_PAGE_PARAMS));
		assertEquals(HttpStatus.BAD_REQUEST, apiExceptionHandler.resolveHttpStatus(ErrorCode.ERR_CODE_SEARCH_CANNOT_HAVE_BOTH_CONDITION_AND_FETCHWITHOUTCONDITION));
		assertEquals(HttpStatus.BAD_REQUEST, apiExceptionHandler.resolveHttpStatus(ErrorCode.ERR_CODE_SEARCH_REQUIRE_CONDITION_OR_FETCHWITHOUTCONDITION));
	}

}
