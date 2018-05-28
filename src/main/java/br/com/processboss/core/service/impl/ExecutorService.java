package br.com.processboss.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.processboss.core.exception.ProcessExecutionException;
import br.com.processboss.core.model.ProcessExecutionDetail;
import br.com.processboss.core.model.ProcessInTask;
import br.com.processboss.core.persistence.dao.IProcessExecutionDetailDAO;
import br.com.processboss.core.persistence.dao.IProcessInTaskDAO;
import br.com.processboss.core.scheduling.executor.TaskExecutationManager;
import br.com.processboss.core.service.IExecutorService;
import br.com.processboss.core.service.IServerStateService;
import br.com.processboss.core.thread.ProcessExecutorThread;

@Transactional(propagation=Propagation.REQUIRED)
public class ExecutorService implements IExecutorService {

	private static final Log LOG = LogFactory.getLog(ExecutorService.class);
	
	private IProcessExecutionDetailDAO processExecutionDetailDAO;
	private IProcessInTaskDAO processInTaskDAO;
	private IServerStateService serverStateService;
	
	@Override
	public void executeProcess(ProcessInTask processInTask, String processExecutionKey, TaskExecutationManager manager) throws ProcessExecutionException {
		try {
			ProcessExecutorThread executor = new ProcessExecutorThread(processExecutionKey, processInTask, this, manager, serverStateService);
			executor.start();
		
		} catch (Exception e) {
			LOG.error("Ocorreu um erro ao executar o processo " + processInTask.getProcess().getName());
			throw new ProcessExecutionException("Ocorreu um erro ao executar o processo " + processInTask.getProcess().getName(), e);
		}
	}

	@Override
	public ProcessExecutionDetail saveOrUpdate(
			ProcessExecutionDetail processExecutionDetail) {
		return processExecutionDetailDAO.saveOrUpdate(processExecutionDetail);
	}

	@Override
	public ProcessInTask loadHistory(ProcessInTask process) {
		return processInTaskDAO.loadExecutionDetails(process);
	}

	@Override
	public List<ProcessInTask> loadHistory(List<ProcessInTask> processes) {
		for (ProcessInTask process : processes) {
			process = processInTaskDAO.loadExecutionDetails(process);
		}
		return processes;
	}

	@Override
	public ProcessInTask loadDependencies(ProcessInTask process) {
		return processInTaskDAO.loadDependencies(process);
	}
	
	@Override
	public ProcessInTask prepareToExecution(ProcessInTask process) {
		return processInTaskDAO.initialize(process);
	}

	
	public IProcessExecutionDetailDAO getProcessExecutionDetailDAO() {
		return processExecutionDetailDAO;
	}

	public void setProcessExecutionDetailDAO(
			IProcessExecutionDetailDAO processExecutionDetailDAO) {
		this.processExecutionDetailDAO = processExecutionDetailDAO;
	}

	public IProcessInTaskDAO getProcessInTaskDAO() {
		return processInTaskDAO;
	}

	public void setProcessInTaskDAO(IProcessInTaskDAO processInTaskDAO) {
		this.processInTaskDAO = processInTaskDAO;
	}

	public IServerStateService getServerStateService() {
		return serverStateService;
	}

	public void setServerStateService(IServerStateService serverStateService) {
		this.serverStateService = serverStateService;
	}

}
