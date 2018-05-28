package br.com.processboss.core.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.processboss.core.model.User;
import br.com.processboss.core.persistence.dao.IUserDAO;
import br.com.processboss.core.service.IUserService;


@Transactional(propagation=Propagation.REQUIRED)
public class UserService implements IUserService {

	private IUserDAO userDAO;

	@Override
	public User findById(Long id) {
		return userDAO.findById(id);
	}

	@Override
	public List<User> listAll() {
		return userDAO.listAll();
	}

	@Override
	public User save(User entity) {
		return userDAO.save(entity);
	}

	@Override
	public User update(User entity) {
		return userDAO.save(entity);
	}
	
	@Override
	public User saveOrUpdate(User entity) {
		return userDAO.saveOrUpdate(entity);
	}

	@Override
	public void delete(User entity) {
		userDAO.delete(entity);
	}

	@Override
	public User findByLogin(String login){
		return userDAO.findByLogin(login);
	}

	public IUserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}

}
