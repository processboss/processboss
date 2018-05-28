package br.com.processboss.core.service;

import java.util.List;

import br.com.processboss.core.model.Schedule;
import br.com.processboss.core.model.Task;


public interface IScheduleService extends IService<Schedule, Long> {

	List<Schedule> listByTask(Task task);
	
}
