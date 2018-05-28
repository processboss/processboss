package br.com.processboss.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import br.com.processboss.core.model.Process;
import br.com.processboss.core.model.ProcessInTask;
import br.com.processboss.core.model.Task;
import br.com.processboss.core.service.IProcessInTaskService;
import br.com.processboss.core.service.IProcessService;
import br.com.processboss.core.service.IScheduleService;
import br.com.processboss.core.service.ITaskService;

@ManagedBean(name = "taskController")
@SessionScoped
public class TaskController extends _Bean {

	private static final long serialVersionUID = -7864504279655890739L;

	@ManagedProperty(name = "taskService", value = "#{taskService}")
	private ITaskService taskService;

	@ManagedProperty(name = "processService", value = "#{processService}")
	private IProcessService processService;

	@ManagedProperty(name = "processInTaskService", value = "#{processInTaskService}")
	private IProcessInTaskService processInTaskService;

	@ManagedProperty(name = "scheduleService", value = "#{scheduleService}")
	private IScheduleService scheduleService;

	private Task entity;
	private Process entityProcess;
	private ProcessInTask entityProcessInTask;

	/*
	 * CONSTRUTORES
	 */
	public TaskController() {
	}

	/*
	 * GETS E SETS
	 */
	public ITaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}

	public IProcessService getProcessService() {
		return processService;
	}

	public void setProcessService(IProcessService processService) {
		this.processService = processService;
	}

	public Task getEntity() {
		return entity;
	}

	public void setEntity(Task entity) {
		this.entity = entity;
	}

	public Process getEntityProcess() {
		return entityProcess;
	}

	public void setEntityProcess(Process entityProcess) {
		this.entityProcess = entityProcess;
	}

	public ProcessInTask getEntityProcessInTask() {
		return entityProcessInTask;
	}

	public void setEntityProcessInTask(ProcessInTask entityProcessInTask) {
		this.entityProcessInTask = entityProcessInTask;
	}

	public IProcessInTaskService getProcessInTaskService() {
		return processInTaskService;
	}

	public void setProcessInTaskService(
			IProcessInTaskService processInTaskService) {
		this.processInTaskService = processInTaskService;
	}

	public IScheduleService getScheduleService() {
		return scheduleService;
	}

	public void setScheduleService(IScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	/*
	 * DESENVOLVIMENTO
	 */
	public List<Task> getAllEntities() {
		return taskService.listAll();
	}

	public String updateEntity() {
		entity = (Task) getJsfParam("entity");
		entity = taskService.loadProcesses(entity);
		return "updateTask";
	}

	public String newEntity() {
		entity = new Task();
		return "newTask";
	}

	public String cancel() {
		return "index";
	}

	public String saveOrUpdate() {
		if (entity != null) {
			taskService.saveOrUpdate(entity);
			addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Tarefa inserido/alterado com suscesso", ""));
			return "index";
		} else {
			addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Nao foi possivel salvar/alterar a tarefa.", ""));
			return null;
		}
	}

	public void captureTask(ActionEvent action) {
		entity = (Task) getJsfParam("entity");
	}

	public String delete() {
		if (entity != null) {
			taskService.delete(entity);
			addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Tarefa removido com sucesso", ""));
			return "index";
		} else {
			addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Nao foi possivel excluir a tarefa", ""));
			return null;
		}
	}

	public List<Process> getAllProcesses() {
		return processService.listAll();
	}

	public void addProcess(ActionEvent event) {
		ProcessInTask pit = new ProcessInTask();
		pit.setProcess(entityProcess);
		entity.getProcesses().add(pit);
	}

	/**
	 * Este método é chamado quando há a necessidade de adição de dependência
	 * entre processos de uma tarefa. Antes de efetuar o encaminhamento para a
	 * próxima página, o método salva o estado atual da tarefa e atualiza o
	 * objeto da sessão.
	 * 
	 * @return Caminho da página de adição de dependência
	 */
	public String newDependencies() {
		if (entity != null) {
			entity = taskService.saveOrUpdate(entity);
			addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Tarefa inserido/alterado com suscesso", ""));
			return "newDependencies";
		} else {
			addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Nao foi possivel salvar/alterar a tarefa.", ""));
			return null;
		}
	}

	/**
	 * @return Caminho da página de adição de dependência
	 */
	public String addDependenciePage() {
		entityProcessInTask = (ProcessInTask) getJsfParam("entityProcess");
		entityProcessInTask = processInTaskService.loadDependencies(entityProcessInTask);
		return "addDependencie";
	}

	public void addDependencie(ActionEvent event) {
		for (ProcessInTask p : entity.getProcesses()) {
			if(p.getProcess().getId().equals(entityProcess.getId())){
				entityProcessInTask.getDependencies().add(p);
				break;
			}
		}
	}

	public String saveOrUpdateDependency() {
		if (entityProcessInTask != null) {
			processInTaskService.saveOrUpdate(entityProcessInTask);
		}
		return "newDependencies";
	}

	public List<Process> getAllProcessesDependencies() {
		List<Process> processes = new ArrayList<Process>();
		for (ProcessInTask pid : entity.getProcesses()) {
			if (!pid.getProcess().equals(entityProcessInTask.getProcess()))
				processes.add(pid.getProcess());
		}
		return processes;
	}

}
