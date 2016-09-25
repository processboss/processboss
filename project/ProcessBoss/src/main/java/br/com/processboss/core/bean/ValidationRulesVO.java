package br.com.processboss.core.bean;

import java.io.Serializable;
import java.util.Map;

import br.com.processboss.core.model.ProcessInTask;
import br.com.processboss.core.service.IServerStateService;

public class ValidationRulesVO implements Serializable {

	private static final long serialVersionUID = 2337466084358724954L;

	private ProcessInTask processInTask;
	private Map<Long, ProcessInTask> executed;
	private IServerStateService serverStateService;

	public ValidationRulesVO() {
		// TODO Auto-generated constructor stub
	}

	public ProcessInTask getProcessInTask() {
		return processInTask;
	}

	public void setProcessInTask(ProcessInTask processInTask) {
		this.processInTask = processInTask;
	}

	public Map<Long, ProcessInTask> getExecuted() {
		return executed;
	}

	public void setExecuted(Map<Long, ProcessInTask> executed) {
		this.executed = executed;
	}

	public IServerStateService getServerStateService() {
		return serverStateService;
	}

	public void setServerStateService(IServerStateService serverStateService) {
		this.serverStateService = serverStateService;
	}

}
