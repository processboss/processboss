package br.com.processboss.core.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.processboss.core.model.Schedule;
import br.com.processboss.core.model.Task;
import br.com.processboss.core.persistence.dao.IScheduleDAO;
import br.com.processboss.core.service.IScheduleService;


@Transactional(propagation=Propagation.REQUIRED)
public class ScheduleService implements IScheduleService {

	private IScheduleDAO scheduleDAO;

	@Override
	public Schedule findById(Long id) {
		return scheduleDAO.findById(id);
	}

	@Override
	public List<Schedule> listAll() {
		return scheduleDAO.listAll();
	}

	@Override
	public Schedule save(Schedule entity) {
		return scheduleDAO.save(entity);
	}
	
	@Override
	public Schedule update(Schedule entity) {
		return scheduleDAO.save(entity);
	}

	@Override
	public Schedule saveOrUpdate(Schedule entity) {
		return scheduleDAO.saveOrUpdate(entity);
	}

	@Override
	public void delete(Schedule entity) {
		scheduleDAO.delete(entity);
	}
	
	@Override
	public List<Schedule> listByTask(Task task) {
		return scheduleDAO.listByTask(task);
	}

	public IScheduleDAO getScheduleDAO() {
		return scheduleDAO;
	}

	public void setScheduleDAO(IScheduleDAO scheduleDAO) {
		this.scheduleDAO = scheduleDAO;
	}


}
