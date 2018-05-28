package br.com.processboss.core.persistence.dao;

import br.com.processboss.core.model.User;

public interface IUserDAO extends IDAO<User, Long> {

	User findByLogin(String login);

}
