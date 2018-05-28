package br.com.processboss.core.service;

import java.util.List;

import br.com.processboss.core.exception.ProcessExecutionException;
import br.com.processboss.core.model.ProcessExecutionDetail;
import br.com.processboss.core.model.ProcessInTask;
import br.com.processboss.core.scheduling.executor.TaskExecutationManager;

public interface IExecutorService {

	ProcessInTask prepareToExecution(ProcessInTask process);
	void executeProcess(ProcessInTask processInTask, String processExecutionKey, TaskExecutationManager manager) throws ProcessExecutionException;
	ProcessExecutionDetail saveOrUpdate(ProcessExecutionDetail processExecutionDetail);
	ProcessInTask loadHistory(ProcessInTask process);
	List<ProcessInTask> loadHistory(List<ProcessInTask> processes);
	ProcessInTask loadDependencies(ProcessInTask process);
}
