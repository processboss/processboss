package br.com.processboss.core.service;

import java.util.List;

import br.com.processboss.core.bean.Resources;
import br.com.processboss.core.bean.ServerState;
import br.com.processboss.core.model.ProcessInTask;

public interface IServerStateService {

	/**
	 * Le as informacoes de hardware do servidor
	 * 
	 * @return ServerState o estado atual do servidor
	 */
	ServerState read();
	
	String addProcessExecution(ProcessInTask processInTask);
	
	void removeProcessExecution(String processExecutionKey);
	
	void addProcessWaiting(ProcessInTask processInTask);
	
	void removeProcessWaiting(ProcessInTask processInTask);
	
	List<ProcessInTask> getInProgressProcess();

	List<ProcessInTask> getWaitingProcess();
	
	void updateResourcesConsumption(int pid, Resources resources);
	
	void removeResourcesConsumption(int pid);

	Resources getAlocatedResources();
	
	Resources getResourcesConsumption();
	
	Resources getAvaiableResources();
	
}
