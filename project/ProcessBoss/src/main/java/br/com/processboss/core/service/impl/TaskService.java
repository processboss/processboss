package br.com.processboss.core.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.processboss.core.model.Process;
import br.com.processboss.core.model.ProcessInTask;
import br.com.processboss.core.model.Task;
import br.com.processboss.core.persistence.dao.IProcessExecutionDetailDAO;
import br.com.processboss.core.persistence.dao.IProcessInTaskDAO;
import br.com.processboss.core.persistence.dao.IScheduleDAO;
import br.com.processboss.core.persistence.dao.ITaskDAO;
import br.com.processboss.core.service.ITaskService;


@Transactional(propagation=Propagation.REQUIRED)
public class TaskService implements ITaskService {

	private ITaskDAO taskDAO;
	private IScheduleDAO scheduleDAO;
	private IProcessExecutionDetailDAO processExecutionDetailDAO;
	private IProcessInTaskDAO processInTaskDAO;

	@Override
	public Task findById(Long id) {
		return taskDAO.findById(id);
	}

	@Override
	public List<Task> listAll() {
		return taskDAO.listAll();
	}

	@Override
	public Task save(Task entity) {
		return taskDAO.save(entity);
	}
	
	@Override
	public Task update(Task entity) {
		return taskDAO.save(entity);
	}

	@Override
	public Task saveOrUpdate(Task entity) {
		return taskDAO.saveOrUpdate(entity);
	}

	@Override
	public void delete(Task task) {
		
		task = taskDAO.loadProcesses(task);
		for (ProcessInTask processInTask : task.getProcesses()) {
			processInTaskDAO.loadExecutionDetails(processInTask);
			processInTask.getExecutionDetails().clear();
			processInTaskDAO.saveOrUpdate(processInTask);
		}
		task.getProcesses().clear();
		taskDAO.saveOrUpdate(task);
		
		scheduleDAO.deleteByTask(task);
		taskDAO.delete(task);
	}

	@Override
	public Task loadProcesses(Task task) {
		return taskDAO.loadProcesses(task);
	}
	
	@Override
	public List<Task> findByProcess(Process process) {
		return taskDAO.findByProcess(process);
	}

	public ITaskDAO getTaskDAO() {
		return taskDAO;
	}

	public void setTaskDAO(ITaskDAO taskDAO) {
		this.taskDAO = taskDAO;
	}

	public IScheduleDAO getScheduleDAO() {
		return scheduleDAO;
	}

	public void setScheduleDAO(IScheduleDAO scheduleDAO) {
		this.scheduleDAO = scheduleDAO;
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

}
