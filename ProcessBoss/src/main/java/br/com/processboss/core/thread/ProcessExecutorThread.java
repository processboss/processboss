package br.com.processboss.core.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.processboss.core.model.Process;
import br.com.processboss.core.model.ProcessInTask;
import br.com.processboss.core.scheduling.executor.TaskExecutationManager;
import br.com.processboss.core.service.IExecutorService;
import br.com.processboss.core.service.IServerStateService;

public class ProcessExecutorThread implements Runnable {

	private static final Log LOG = LogFactory.getLog(ProcessExecutorThread.class);
	
	Thread runner;
	ProcessMonitorThread monitor;
	
	ProcessInTask processInTask;
	Process process;
	String name;
	
	TaskExecutationManager manager;

	private final IServerStateService serverStateService;
	
	public ProcessExecutorThread(String name, ProcessInTask processInTask, IExecutorService executorService, TaskExecutationManager manager, IServerStateService serverStateService) {
		this.processInTask = processInTask;
		this.serverStateService = serverStateService;
		this.process = processInTask.getProcess();
		this.name = name;
		this.manager = manager;
		runner = new Thread(this, name);
		monitor = new ProcessMonitorThread(processInTask, executorService, this.serverStateService);
	}
	
	public void start(){
		runner.start();
	}
	
	public boolean isRunning(){
		return runner.isAlive();
	}
	
	@Override
	public void run() {
		
		LOG.debug("Iniciando a execucao do processo: " + process.getName());
		
		try {
			ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c","echo $$ && " +process.getPath());
			java.lang.Process p = builder.start();
			
			int pid = getPID(p);

			monitor.start(pid);
			
			p.waitFor();
			
			monitor.stop();
			
			if(manager != null){
				manager.processTerminated(processInTask, name);
			}
			
			LOG.debug("Execucao do processo: " + process.getName() + " finalizada!");

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Ocorreu um erro ao executar o processo " + process.getName());
		}
		
	}
	
	protected int getPID(java.lang.Process p) throws NumberFormatException, IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		return Integer.parseInt(br.readLine()) + 1;
	}

}
