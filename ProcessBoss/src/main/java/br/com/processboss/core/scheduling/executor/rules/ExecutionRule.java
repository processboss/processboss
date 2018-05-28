package br.com.processboss.core.scheduling.executor.rules;

import br.com.processboss.core.bean.ValidationRulesVO;

public abstract class ExecutionRule {

	private ExecutionRule next;
	
	public ExecutionRule(ExecutionRule next) {
		this.next = next;
	}
	
	public boolean canExecute(ValidationRulesVO validationRulesVO){
		if(!validate(validationRulesVO)){
			return false;
		}
		
		if(next != null){
			return next.canExecute(validationRulesVO);
		}
		
		return true;
	}
	
	public abstract boolean validate(ValidationRulesVO validationRulesVO);
	
}
