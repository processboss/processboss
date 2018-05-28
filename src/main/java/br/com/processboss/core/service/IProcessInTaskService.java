package br.com.processboss.core.service;

import java.util.List;
import br.com.processboss.core.model.Process;

import br.com.processboss.core.model.ProcessInTask;


public interface IProcessInTaskService extends IService<ProcessInTask, Long> {

	ProcessInTask loadExecutionDetails(ProcessInTask processInTask);
	List<ProcessInTask> findByProcess(Process process);
	ProcessInTask loadDependencies(ProcessInTask processInTask);
	
}
