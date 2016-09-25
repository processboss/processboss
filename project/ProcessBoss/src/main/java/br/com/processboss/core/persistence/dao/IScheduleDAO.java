package br.com.processboss.core.persistence.dao;

import java.util.List;

import br.com.processboss.core.model.Schedule;
import br.com.processboss.core.model.Task;


public interface IScheduleDAO extends IDAO<Schedule, Long> {

	List<Schedule> listByTask(Task task);
	void deleteByTask(Task task);
	
}
