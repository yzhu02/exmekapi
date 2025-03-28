package com.exmek.core.scheduler;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.exmek.core.config.AppConfigProvider;

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
		if (scheduleables != null) {
			scheduleables.forEach(Scheduleable::onSchedule);
		}
	}
}
