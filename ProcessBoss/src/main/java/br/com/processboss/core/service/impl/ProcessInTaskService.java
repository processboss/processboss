package br.com.processboss.core.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.processboss.core.model.Process;
import br.com.processboss.core.model.ProcessInTask;
import br.com.processboss.core.persistence.dao.IProcessInTaskDAO;
import br.com.processboss.core.service.IProcessInTaskService;


@Transactional(propagation=Propagation.REQUIRED)
public class ProcessInTaskService implements IProcessInTaskService {

	private IProcessInTaskDAO processInTaskDAO;

	@Override
	public ProcessInTask findById(Long id) {
		return processInTaskDAO.findById(id);
	}

	@Override
	public List<ProcessInTask> listAll() {
		return processInTaskDAO.listAll();
	}

	@Override
	public ProcessInTask save(ProcessInTask entity) {
		return processInTaskDAO.save(entity);
	}

	@Override
	public ProcessInTask update(ProcessInTask entity) {
		return processInTaskDAO.update(entity);
	}
	
	@Override
	public ProcessInTask saveOrUpdate(ProcessInTask entity) {
		return processInTaskDAO.saveOrUpdate(entity);
	}

	@Override
	public void delete(ProcessInTask entity) {
		processInTaskDAO.delete(entity);
	}

	public IProcessInTaskDAO getProcessInTaskDAO() {
		return processInTaskDAO;
	}

	public void setProcessInTaskDAO(IProcessInTaskDAO processInTaskDAO) {
		this.processInTaskDAO = processInTaskDAO;
	}

	@Override
	public ProcessInTask loadExecutionDetails(ProcessInTask processInTask) {
		return processInTaskDAO.loadExecutionDetails(processInTask);
	}

	@Override
	public List<ProcessInTask> findByProcess(Process process) {
		return processInTaskDAO.findByProcess(process);
	}

	@Override
	public ProcessInTask loadDependencies(ProcessInTask processInTask) {
		return processInTaskDAO.loadDependencies(processInTask);
	}


}
