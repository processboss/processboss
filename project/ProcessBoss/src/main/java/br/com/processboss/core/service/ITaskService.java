package br.com.processboss.core.service;

import java.util.List;

import br.com.processboss.core.model.Process;
import br.com.processboss.core.model.Task;

public interface ITaskService extends IService<Task, Long> {

	Task loadProcesses(Task task);
	List<Task> findByProcess(Process process);
	
}
