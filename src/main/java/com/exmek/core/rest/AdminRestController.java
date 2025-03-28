package com.exmek.core.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exmek.core.consts.EndpointConsts;
import com.exmek.core.scheduler.ScheduleListener;

@RestController
@RequestMapping(EndpointConsts.ENDPOINT_ADMIN)
public class AdminRestController {

	@Autowired
	protected ScheduleListener scheduleListener;
	
	@PostMapping("/refresh")
	public void triggerSchedule() {
		scheduleListener.trigger();
	}

}
