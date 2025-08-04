package com.exmek.core.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

	String ERR_CODE_UNAUTHORIZED				= String.valueOf(HttpStatus.UNAUTHORIZED.value());
	
	String ERR_CODE_FORBIDDEN					= String.valueOf(HttpStatus.FORBIDDEN.value());
	
	String ERR_CODE_UNCAUGHT_EXCEPTION			= String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
	
	String ERR_CODE_HEALTHCHECK_DB_QUERY_FAILED			= String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()) + ".db_query_failed";
	
	String ERR_CODE_HEALTHCHECK_MISSING_MOTOR_CONFIG	= String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()) + ".missing_motor_config";
	
	String ERR_CODE_LINEAR_STEPPER_MOTOR_PERF_CURVE_DATA_MALFORMED =
			String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()) + ".stepper_motor.linear.perf_curve_data.malformed";
	
	String ERR_CODE_INQUIRY_MISSING_REQUEST_PAYLOAD =
			String.valueOf(HttpStatus.BAD_REQUEST.value()) + ".inquiry.missing_request_payload";
	
	String ERR_CODE_DOWNLOAD_TRACKING_MISSING_REQUEST_PAYLOAD =
			String.valueOf(HttpStatus.BAD_REQUEST.value()) + ".download.tracking.missing_request_payload";
	
	String ERR_CODE_REQUIRE_BOTH_OR_NONE_PAGE_PARAMS =
			String.valueOf(HttpStatus.BAD_REQUEST.value()) + ".request.pagination.require_both_or_none_page_params";
	
	String ERR_CODE_SEARCH_CANNOT_HAVE_BOTH_CONDITION_AND_FETCHWITHOUTCONDITION =
			String.valueOf(HttpStatus.BAD_REQUEST.value()) + ".request.search.cannot_have_both_condition_and_fetchWithoutCondition";
	
	String ERR_CODE_SEARCH_REQUIRE_CONDITION_OR_FETCHWITHOUTCONDITION =
			String.valueOf(HttpStatus.BAD_REQUEST.value()) + ".request.search.require_condition_or_fetchWithoutCondition";
	
}
