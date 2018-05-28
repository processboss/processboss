package br.com.processboss.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.processboss.core.bean.Resources;
import br.com.processboss.core.bean.ServerState;
import br.com.processboss.core.model.ProcessInTask;
import br.com.processboss.core.service.IServerStateService;

/**
 * Servico que obtem as informacoes de hardware
 * do servidor no momento solicitado
 */
public class ServerStateService implements IServerStateService {

	private static final Logger LOG = LoggerFactory.getLogger(ServerStateService.class); 
	
	private static final Map<String, ProcessInTask> IN_PROGRESS = new HashMap<String, ProcessInTask>();
	private static final List<ProcessInTask> WAITING = new ArrayList<ProcessInTask>();
	
	private static final Map<Integer, Resources> resourcesConsumption = new HashMap<Integer, Resources>();
	
	/**
	 * Le as informacoes de hardware do servidor
	 * 
	 * @return
	 */
	@Override
	public ServerState read() {
		ServerState serverState = new ServerState();
		try {
			Sigar.load();
			Sigar sigar = new Sigar();

			/**
			 * Informacoes da CPU
			 */
			serverState.setCpuInfo(sigar.getCpuInfoList());
			serverState.setCpuPerc(sigar.getCpuPerc());
			
			/**
			 * Informacoes da Memoria
			 */
			serverState.setMemory(sigar.getMem());
			
			/**
			 * Consumo de recursos pelos processos
			 */
			serverState.setResourcesConsumption(getResourcesConsumption());
		
		} catch (SigarException e) {
			LOG.error("Ocorreu um erro ao obter as informacoes do servidor.");
			e.printStackTrace();
		}
		
		return serverState;
	}
	
	@Override
	public Resources getAlocatedResources(){
		double memory = 0.0D;
		double cpu = 0.0D;
		for (ProcessInTask process : IN_PROGRESS.values()) {
			Resources resources = new Resources(process.getExecutionDetails());
			memory += resources.getMemory();
			cpu += resources.getCpu();
		}
		return new Resources(memory, cpu);
	}

	@Override
	public String addProcessExecution(ProcessInTask processInTask) {
		String key = processInTask.getId() + "_" + new Date().getTime();
		
		IN_PROGRESS.put(key, processInTask);
		
		return key;
	}

	@Override
	public void removeProcessExecution(String processExecutionKey) {
		IN_PROGRESS.remove(processExecutionKey);
	}
	
	@Override
	public void addProcessWaiting(ProcessInTask processInTask) {
		WAITING.add(processInTask);
		
	}

	@Override
	public void removeProcessWaiting(ProcessInTask processInTask) {
		WAITING.remove(processInTask);
	}
	
	@Override
	public List<ProcessInTask> getInProgressProcess(){
		List<ProcessInTask> list = new ArrayList<ProcessInTask>();
		for (ProcessInTask process : IN_PROGRESS.values()) {
			list.add(process);
		}
		return list;
	}
	
	@Override
	public List<ProcessInTask> getWaitingProcess(){
		List<ProcessInTask> list = new ArrayList<ProcessInTask>();
		for (ProcessInTask process : WAITING) {
			list.add(process);
		}
		return list;
	}

	@Override
	public synchronized void updateResourcesConsumption(int pid, Resources resources) {
		resourcesConsumption.put(pid, resources); 
	}

	@Override
	public synchronized void removeResourcesConsumption(int pid) {
		resourcesConsumption.remove(pid); 
	}

	@Override
	public Resources getResourcesConsumption() {
		double mem = 0D, cpu=0D;
		for (Resources resources : resourcesConsumption.values()) {
			mem += resources.getMemory();
			cpu += resources.getCpu();
		}
		return new Resources(mem, cpu);
	}

	@Override
	public Resources getAvaiableResources() {
		ServerState serverState = read();
		Resources alocatedResources = getAlocatedResources();
		
		long totalMemory = serverState.getMemory().getTotal();
		long usedMemory = serverState.getMemory().getUsed();
		Double consumedMemory = serverState.getResourcesConsumption().getMemory();
		Double alocatedMemory = alocatedResources.getMemory();
		
		Double freeMemory = totalMemory - ((usedMemory - consumedMemory) + alocatedMemory);
		
		
		int totalCpu = 100;
		double usedCpu = serverState.getCpuUsed();
		Double consumedCpu = serverState.getResourcesConsumption().getCpu();
		Double alocatedCpu = alocatedResources.getCpu();
		
		Double freeCpu = totalCpu - ((usedCpu - consumedCpu) + alocatedCpu);
		
		return new Resources(freeMemory, freeCpu);
	}
	
}
