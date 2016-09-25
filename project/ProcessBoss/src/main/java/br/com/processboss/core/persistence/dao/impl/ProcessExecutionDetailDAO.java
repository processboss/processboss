package br.com.processboss.core.persistence.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.processboss.core.model.ProcessExecutionDetail;
import br.com.processboss.core.model.ProcessInTask;
import br.com.processboss.core.persistence.dao.IProcessExecutionDetailDAO;

public class ProcessExecutionDetailDAO extends GenericDAO<ProcessExecutionDetail, Long> implements IProcessExecutionDetailDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcessExecutionDetail> listByProcessInTask(ProcessInTask processInTask) {
		Criteria criteria = getSession().createCriteria(ProcessExecutionDetail.class);
		criteria.createAlias("processInTask", "processInTask");
		criteria.add(Restrictions.eq("processInTask.id", processInTask.getId()));
		return criteria.list();
	}
	
	@Override
	public void deleteByProcessInTask(ProcessInTask processInTask) {
		List<ProcessExecutionDetail> details = listByProcessInTask(processInTask);
		if(!CollectionUtils.isEmpty(details)){
			Session session = getSession();
			this.getHibernateTemplate().deleteAll(details);
			session.flush();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcessExecutionDetail> getHistory(ProcessInTask processInTask, int limit) {
		Criteria criteria = getSession().createCriteria(ProcessExecutionDetail.class);
		criteria.createAlias("processInTask", "processInTask");
		criteria.add(Restrictions.eq("processInTask.id", processInTask.getId()));
		criteria.setMaxResults(limit);
		return criteria.list();
	}

}
