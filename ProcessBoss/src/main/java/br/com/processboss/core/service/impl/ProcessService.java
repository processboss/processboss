package br.com.processboss.core.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.processboss.core.model.Process;
import br.com.processboss.core.persistence.dao.IProcessDAO;
import br.com.processboss.core.service.IProcessService;


@Transactional(propagation=Propagation.REQUIRED)
public class ProcessService implements IProcessService {

	private IProcessDAO processDAO;

	@Override
	public Process findById(Long id) {
		return processDAO.findById(id);
	}

	@Override
	public List<Process> listAll() {
		return processDAO.listAll();
	}

	@Override
	public Process save(Process entity) {
		return processDAO.save(entity);
	}
	
	@Override
	public Process update(Process entity) {
		return processDAO.update(entity);
	}

	@Override
	public Process saveOrUpdate(Process entity) {
		return processDAO.saveOrUpdate(entity);
	}

	@Override
	public void delete(Process entity) {
		processDAO.delete(entity);
	}

	public IProcessDAO getProcessDAO() {
		return processDAO;
	}

	public void setProcessDAO(IProcessDAO processDAO) {
		this.processDAO = processDAO;
	}


}
