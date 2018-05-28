package br.com.processboss.core.thread;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.Sigar;

import br.com.processboss.core.bean.Resources;
import br.com.processboss.core.model.ExecutionStatus;
import br.com.processboss.core.model.ProcessExecutionDetail;
import br.com.processboss.core.model.ProcessInTask;
import br.com.processboss.core.service.IExecutorService;
import br.com.processboss.core.service.IServerStateService;

public class ProcessMonitorThread implements Runnable {

	private static final Log LOG = LogFactory.getLog(ProcessMonitorThread.class);
	
	int pid;
	Thread runner;
	boolean canContinue;
	ProcessExecutionDetail detail;
	
	private IServerStateService serverStateService;
	private IExecutorService executorService;
	
	public ProcessMonitorThread(ProcessInTask processInTask, IExecutorService executorService, IServerStateService serverStateService) {
		this.executorService = executorService;
		this.serverStateService = serverStateService;

		runner = new Thread(this, "monitor_"+pid);
		detail = new ProcessExecutionDetail();
		detail.setProcessInTask(processInTask);
	}
	
	public void start(int pid){
		this.pid = pid;
		
		canContinue = true;
		
		detail.setStart(new Date());
		detail.setStatus(ExecutionStatus.IN_PROGRESS);
		
		executorService.saveOrUpdate(detail);
		
		runner.start();
	}
	
	public void stop(){
		canContinue = false;
		detail.setEnd(new Date());
		
		/**
		 * Se nao falhou, atualizo o status para DONE
		 */
		if(!ExecutionStatus.FAILED.equals(detail.getStatus())){
			detail.setStatus(ExecutionStatus.DONE);
		}
		
		serverStateService.removeResourcesConsumption(pid);
		
		executorService.saveOrUpdate(detail);
	}
	
	@Override
	public void run() {
			
		try {
			Sigar.load();
			Sigar sigar = new Sigar();
			
			ProcMem mem;
			ProcCpu cpu;
			while(canContinue){
				cpu = sigar.getProcCpu(pid);
				mem = sigar.getProcMem(pid);
				
				long valMemory = mem.getResident();
				double valCpu = cpu.getPercent();
				
				detail.add(valMemory, valCpu);
				
				Resources resources = new Resources(valMemory, valCpu);
				serverStateService.updateResourcesConsumption(pid, resources);
				
				System.out.println("Processo " + pid + "- Mem:" + (detail.getMemoryMean()/1024)/1024 + " CPU:" + detail.getCpuMean()*100);
				
				Thread.sleep(500);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Ocorreu um erro ao monitorar o processo " + pid);
			
			detail.setStatus(ExecutionStatus.FAILED);
			
			executorService.saveOrUpdate(detail);
		}
		
	}

}
