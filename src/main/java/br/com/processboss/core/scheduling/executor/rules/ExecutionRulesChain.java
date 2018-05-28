package br.com.processboss.core.scheduling.executor.rules;

import br.com.processboss.core.bean.ValidationRulesVO;

public class ExecutionRulesChain {

	private static final ExecutionRule root;
	
	static{
		ResourcesExecutionRule resourcesExecutionRule = new ResourcesExecutionRule(null);
		root = new DependeciesExecutionRule(resourcesExecutionRule);
	}
	
	public static boolean canExecute(ValidationRulesVO validationRulesVO){
		return root.canExecute(validationRulesVO);
	}
	
}
