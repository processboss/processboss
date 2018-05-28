package br.com.processboss.core.persistence.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;

import br.com.processboss.core.model.Process;
import br.com.processboss.core.model.ProcessInTask;
import br.com.processboss.core.persistence.dao.IProcessInTaskDAO;

public class ProcessInTaskDAO extends GenericDAO<ProcessInTask, Long> implements IProcessInTaskDAO {

	@Override
	public ProcessInTask loadExecutionDetails(ProcessInTask processInTask) {
		processInTask = findById(processInTask.getId());
		if(processInTask != null){
			Hibernate.initialize(processInTask.getExecutionDetails());
		}
		return processInTask;
	}

	@Override
	public ProcessInTask loadDependencies(ProcessInTask processInTask) {
		processInTask = findById(processInTask.getId());
		if(processInTask != null){
			Hibernate.initialize(processInTask.getDependencies());
		}
		return processInTask;
	}

	@Override
	public ProcessInTask initialize(ProcessInTask processInTask) {
		processInTask = findById(processInTask.getId());
		if(processInTask != null){
			Hibernate.initialize(processInTask.getExecutionDetails());
			Hibernate.initialize(processInTask.getDependencies());
		}
		return processInTask;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcessInTask> findByProcess(Process process) {
		Criteria criteria = getSession().createCriteria(ProcessInTask.class);
		criteria.createAlias("process", "process");
		criteria.add(Restrictions.eq("process.id", process.getId()));
		return criteria.list();
	}

}
