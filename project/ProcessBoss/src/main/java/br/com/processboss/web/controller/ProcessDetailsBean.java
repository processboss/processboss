package br.com.processboss.web.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.chart.PieChartModel;

import br.com.processboss.core.model.ExecutionStatus;
import br.com.processboss.core.model.Process;
import br.com.processboss.core.model.ProcessExecutionDetail;
import br.com.processboss.core.model.ProcessInTask;
import br.com.processboss.core.model.Task;
import br.com.processboss.core.service.IProcessInTaskService;
import br.com.processboss.core.service.IServerStateService;
import br.com.processboss.core.service.ITaskService;

@ManagedBean(name="processDetailsBean")
@RequestScoped
public class ProcessDetailsBean extends _Bean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(name="serverStateService", value="#{serverStateService}")
	private IServerStateService serverStateService;
	
	@ManagedProperty(name="processInTaskService", value="#{processInTaskService}")
	private IProcessInTaskService processInTaskService;
	
	@ManagedProperty(name="taskService", value="#{taskService}")
	private ITaskService taskService;
	
	@ManagedProperty(name="process", value="#{processController.entity}")
	private Process process;
	
	private PieChartModel executionStatisticsChart;

	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	
    public ProcessDetailsBean() {  
    	
    }  
    
    public PieChartModel getExecutionStatisticsChart(){
    	
    	int success = 0;
    	int failures = 0;
    	
    	List<ProcessInTask> list = processInTaskService.findByProcess(process);
    	for (ProcessInTask processInTask : list) {
			
    		processInTask = processInTaskService.loadExecutionDetails(processInTask);
    		
    		for (ProcessExecutionDetail detail : processInTask.getExecutionDetails()) {
				
    			if(ExecutionStatus.DONE.equals(detail.getStatus())){
    				success++;
    			}else if(ExecutionStatus.FAILED.equals(detail.getStatus())){
    				failures++;
    			}
    			
			}
    		
		}
    	
    	executionStatisticsChart = new PieChartModel();
    	
    	executionStatisticsChart.set("Sucesso", success);
    	executionStatisticsChart.set("Falha", failures);
    	
    	return executionStatisticsChart;
    }
    
    public List<Task> getTasks(){
    	return taskService.findByProcess(process);
    }
    
    public List<ProcessInTask> getMemoryMean(){
    	//TODO: calcular a media de memoria
    	return serverStateService.getInProgressProcess();
    }
  
    public List<ProcessInTask> getActiveProcesses(){
    	return serverStateService.getInProgressProcess();
    }
    
    public List<ProcessInTask> getWaitProcesses(){
    	return serverStateService.getWaitingProcess();
    }
    
	public IServerStateService getServerStateService() {
		return serverStateService;
	}

	public void setServerStateService(IServerStateService serverStateService) {
		this.serverStateService = serverStateService;
	}
	
	public IProcessInTaskService getProcessInTaskService() {
		return processInTaskService;
	}

	public void setProcessInTaskService(IProcessInTaskService processInTaskService) {
		this.processInTaskService = processInTaskService;
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public ITaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}
	
}
