package br.com.processboss.core.persistence.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;

import br.com.processboss.core.model.Process;
import br.com.processboss.core.model.Task;
import br.com.processboss.core.persistence.dao.ITaskDAO;

public class TaskDAO extends GenericDAO<Task, Long> implements ITaskDAO {

	@Override
	public Task loadProcesses(Task task) {
		task = findById(task.getId());
		Hibernate.initialize(task.getProcesses());
		return task;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> findByProcess(Process process) {
		Criteria criteria = getSession().createCriteria(Task.class);
		criteria.createAlias("processes", "processInTask");
		criteria.add(Restrictions.eq("processInTask.process.id", process.getId()));
		return criteria.list();
	}

}
