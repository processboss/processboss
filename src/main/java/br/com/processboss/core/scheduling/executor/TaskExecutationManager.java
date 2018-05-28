package br.com.processboss.core.scheduling.executor;

import br.com.processboss.core.model.ProcessInTask;

public interface TaskExecutationManager {

	void processTerminated(ProcessInTask processInTask, String processExecutionKey);
	
}
