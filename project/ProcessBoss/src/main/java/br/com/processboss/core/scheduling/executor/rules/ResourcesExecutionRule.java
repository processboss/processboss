package br.com.processboss.core.scheduling.executor.rules;

import br.com.processboss.core.bean.Resources;
import br.com.processboss.core.bean.ServerState;
import br.com.processboss.core.bean.ValidationRulesVO;
import br.com.processboss.core.model.ProcessInTask;
import br.com.processboss.core.service.IServerStateService;

/**
 * Classe com regra que verifica os recursos de hardware
 */
public class ResourcesExecutionRule extends ExecutionRule {

	public ResourcesExecutionRule(ExecutionRule next) {
		super(next);
	}

	@Override
	public boolean validate(ValidationRulesVO validationRulesVO) {
		IServerStateService serverStateService = validationRulesVO.getServerStateService();
		ProcessInTask processInTask = validationRulesVO.getProcessInTask();
		
		ServerState serverState = serverStateService.read();
		long serverMemory = serverState.getMemory().getTotal();
		
		Resources avaiableResources = serverStateService.getAvaiableResources();
		
		Resources requiredResources = new Resources(processInTask.getExecutionDetails());
		
		Double memoryTotal = avaiableResources.getMemory() + requiredResources.getMemory();
		Double cpuTotal = avaiableResources.getCpu() + requiredResources.getCpu();
		
		if(memoryTotal > serverMemory || cpuTotal > Resources.MAX_CPU){
			return false; 
		}
		
		return true;
	}

}
