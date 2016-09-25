package br.com.processboss.core.persistence.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.com.processboss.core.model.User;
import br.com.processboss.core.persistence.dao.IUserDAO;

public class UserDAO extends GenericDAO<User, Long> implements IUserDAO {

	@Override
	public User findByLogin(String login) {
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("login", login));
		return (User) criteria.uniqueResult();
	}

}
