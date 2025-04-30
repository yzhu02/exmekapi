package com.exmek.core.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exmek.core.config.MotorConfigProvider;
import com.exmek.core.consts.EndpointConsts;
import com.exmek.core.error.BizRuntimeException;
import com.exmek.core.error.ErrorCode;
import com.exmek.core.persistence.entity.DCMotorEntity;
import com.exmek.core.persistence.repository.DCMotorRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(EndpointConsts.ENDPOINT_HEALTH)
public class HealthCheckRestController {
	
	@Autowired
	private DCMotorRepository dcMotorRepository;

	@Autowired
	private MotorConfigProvider motorConfigProvider;

	@GetMapping
	public String healthCheck() {
		log.info("Health checking...");

		//Making sure connecting to db is working and db query is working
		Optional<DCMotorEntity> opMotorFound = dcMotorRepository.findByModel("MB057GA100");
		if (!opMotorFound.isPresent()) {
			throw new BizRuntimeException("Health check failed due to unable to connect and/or query to db to find motor. ", ErrorCode.ERR_CODE_HEALTHCHECK_DB_QUERY_FAILED);
		}
		int motorCurveCoordinateCount = motorConfigProvider.getMotorCurveCoordinateCount();
		if (motorCurveCoordinateCount < 36) { //Initially there is 36 configured, by time going it may be more
			throw new BizRuntimeException("Health check failed due to missing or insufficient motor config. ", ErrorCode.ERR_CODE_HEALTHCHECK_MISSING_MOTOR_CONFIG);
		}
		return "Health OK";
	}
}
