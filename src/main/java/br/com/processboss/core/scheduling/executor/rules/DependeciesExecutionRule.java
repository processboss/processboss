package br.com.processboss.core.scheduling.executor.rules;

import java.util.Set;

import br.com.processboss.core.bean.ValidationRulesVO;
import br.com.processboss.core.model.ProcessInTask;

/**
 * Classe com as regras de dependencias
 */
public class DependeciesExecutionRule extends ExecutionRule {

	public DependeciesExecutionRule(ExecutionRule next) {
		super(next);
	}

	@Override
	public boolean validate(ValidationRulesVO validationRulesVO) {
		
		ProcessInTask processInTask = validationRulesVO.getProcessInTask();
		
		Set<ProcessInTask> dependencies = processInTask.getDependencies();
		if(dependencies != null && !dependencies.isEmpty()){
			for (ProcessInTask dependency : dependencies) {
				if(!validationRulesVO.getExecuted().containsKey(dependency.getId())){
					return false;
				}
			}
		}
		
		return true;
	}

}
