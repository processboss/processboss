package br.com.processboss.core.persistence.dao;

import java.util.List;
import br.com.processboss.core.model.Process;

import br.com.processboss.core.model.ProcessInTask;


public interface IProcessInTaskDAO extends IDAO<ProcessInTask, Long> {

	ProcessInTask loadExecutionDetails(ProcessInTask processInTask);
	ProcessInTask loadDependencies(ProcessInTask processInTask);
	ProcessInTask initialize(ProcessInTask process);
	List<ProcessInTask> findByProcess(Process process);
	
}
