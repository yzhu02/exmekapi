package com.exmek.core.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exmek.core.consts.EndpointConsts;
import com.exmek.core.scheduler.ScheduleListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(EndpointConsts.ENDPOINT_ADMIN_PREFIX)
public class AdminRestController {

	@Autowired
	protected ScheduleListener scheduleListener;
	
	@PostMapping("/refresh")
	public void triggerSchedule() {
		log.info("Triggering scheduler registered to the listeners...");
		scheduleListener.trigger();
	}
}
