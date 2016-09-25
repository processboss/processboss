package br.com.processboss.core.scheduling.quartz.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import br.com.processboss.core.bean.ValidationRulesVO;
import br.com.processboss.core.exception.ProcessExecutionException;
import br.com.processboss.core.model.ProcessInTask;
import br.com.processboss.core.model.Task;
import br.com.processboss.core.scheduling.executor.TaskExecutationManager;
import br.com.processboss.core.scheduling.executor.rules.ExecutionRulesChain;
import br.com.processboss.core.service.IExecutorService;
import br.com.processboss.core.service.IServerStateService;
import br.com.processboss.core.service.ITaskService;

/**
 * Job responsavel por disparar a execucao da tarefa
 */
public class TaskJob extends QuartzJobBean implements TaskExecutationManager {
	
	private final static Log LOG = LogFactory.getLog(TaskJob.class);

	private Task task;
	private ITaskService taskService;
	private IExecutorService executorService;
	private IServerStateService serverStateService;
	
	private List<ProcessInTask> toExecute;
	private Map<Long, ProcessInTask> executed = new HashMap<Long, ProcessInTask>();
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		LOG.debug("Iniciando a tarefa " + task.getName());
		
		/**
		 * Busca os processos da tarefa
		 */
		task = taskService.loadProcesses(task);
		List<ProcessInTask> processes = task.getProcesses();
		
		/**
		 * Organiza a fila de execucao
		 */
		organizeProcess(processes);
		
		for (ProcessInTask processInTask : toExecute) {
			serverStateService.addProcessWaiting(processInTask);
		}
		
		try {
			while(!CollectionUtils.isEmpty(toExecute)){
				
				Iterator<ProcessInTask> iterator = toExecute.iterator();
				
				while(iterator.hasNext()){
					
					ProcessInTask processInTask = iterator.next();
					
					/**
					 * Veririca se o processo pode ser executado
					 */
					if(!canExecuteNow(processInTask)){
						continue;
					}
					
					serverStateService.removeProcessWaiting(processInTask);
					String processExecutionKey = serverStateService.addProcessExecution(processInTask);

					/**
					 * Dispara a execucao do processo
					 */
					executorService.executeProcess(processInTask, processExecutionKey, this);
					
					
					/**
					 * Remove o processo da fila
					 */
					iterator.remove();
				}
				
				/**
				 * Se existir algum processo que ainda nao foi executado,
				 * seja por falta de recursos, ou por causa das dependencias,
				 * aguarda um segundo e tenta executar novamente.
				 */
				if(processes.size() != executed.size()){
					Thread.sleep(1000L);
				}
			}
			

		} catch (ProcessExecutionException e) {
			throw new JobExecutionException(e);
		} catch (InterruptedException e) {
			throw new JobExecutionException(e);
		}
			
		
		LOG.debug("Tarefa " + task.getName() + " finalizada!");
	}
	
	/**
	 * Verifica se o processo pode ser executado no momento
	 * 
	 * @param processInTask
	 * @return
	 */
	private boolean canExecuteNow(ProcessInTask processInTask) {
		
		ValidationRulesVO validationRulesVO = new ValidationRulesVO();
		
		validationRulesVO.setServerStateService(serverStateService);
		validationRulesVO.setExecuted(executed);
		validationRulesVO.setProcessInTask(processInTask);
		
		return ExecutionRulesChain.canExecute(validationRulesVO);
	}

	/**
	 * Calcula a ordem de execucao dos processos levando em consideracao 
	 * o historico de execucoes de cada um dos processos.
	 * 
	 * @param processes
	 * @param history
	 */
	protected void organizeProcess(List<ProcessInTask> processes){
		
		toExecute = new ArrayList<ProcessInTask>();
		for (ProcessInTask processInTask : processes) {
			processInTask = executorService.prepareToExecution(processInTask);
			toExecute.add(processInTask);
		}
	}
	
	/**
	 * Informa que o processo terminou de ser executado
	 * e o insere no conjunto de processos concluidos
	 */
	@Override
	public void processTerminated(ProcessInTask processInTask, String processExecutionKey) {
		executed.put(processInTask.getId(), processInTask);
		serverStateService.removeProcessExecution(processExecutionKey);
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}

	public void setExecutorService(IExecutorService executorService) {
		this.executorService = executorService;
	}

	public void setServerStateService(IServerStateService serverStateService) {
		this.serverStateService = serverStateService;
	}

	
}
