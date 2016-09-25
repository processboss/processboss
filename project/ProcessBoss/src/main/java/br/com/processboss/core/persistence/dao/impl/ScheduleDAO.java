package br.com.processboss.core.persistence.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.com.processboss.core.model.Schedule;
import br.com.processboss.core.model.Task;
import br.com.processboss.core.persistence.dao.IScheduleDAO;

public class ScheduleDAO extends GenericDAO<Schedule, Long> implements IScheduleDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Schedule> listByTask(Task task) {
		Criteria criteria = getSession().createCriteria(Schedule.class);
		criteria.createAlias("task", "task");
		criteria.add(Restrictions.eq("task.id", task.getId()));
		return criteria.list();
	}
	
	@Override
	public void deleteByTask(Task task) {
		List<Schedule> schedules = listByTask(task);
		if(!CollectionUtils.isEmpty(schedules)){
			this.getHibernateTemplate().deleteAll(schedules);
		}
	}

}
