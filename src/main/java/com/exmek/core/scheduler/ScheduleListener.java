package com.exmek.core.scheduler;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleListener {

	private final List<Scheduleable> scheduleables;

    public ScheduleListener(List<Scheduleable> scheduleables) {
        this.scheduleables = scheduleables;
    }

	@Scheduled(timeUnit = TimeUnit.MINUTES, fixedRate = 10)
	protected void schedule() {
		if (scheduleables != null) {
			scheduleables.forEach(Scheduleable::onSchedule);
		}
	}

}
