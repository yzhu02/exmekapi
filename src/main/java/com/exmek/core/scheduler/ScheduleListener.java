package com.exmek.core.scheduler;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.exmek.core.config.AppConfigProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ScheduleListener {

	@Autowired
	private AppConfigProvider appConfigProvider;
	
	private final List<Scheduleable> scheduleables;

    public ScheduleListener(List<Scheduleable> scheduleables) {
        this.scheduleables = scheduleables;
    }

	@Scheduled(timeUnit = TimeUnit.MINUTES, fixedRate = 10)
	public void schedule() {
		if (Boolean.FALSE.equals(appConfigProvider.getScheduleEnabled())) {
			return;
		}
		trigger();
	}

	public void trigger() {
		log.info("ScheduleListener is triggered...");
		if (scheduleables != null) {
			scheduleables.forEach(Scheduleable::onSchedule);
		}
	}
}
