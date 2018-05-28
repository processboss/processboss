package br.com.processboss.core.scheduling.quartz;

import java.util.List;

import br.com.processboss.core.exception.ProcessBossException;
import br.com.processboss.core.model.Schedule;

public interface ScheduleManager {

	void addTrigger(Schedule schedule) throws ProcessBossException;
	void addTriggers(List<Schedule> schedules) throws ProcessBossException;
	void removeTrigger(Schedule schedule) throws ProcessBossException;
	void removeTriggers(List<Schedule> schedules) throws ProcessBossException;
}
