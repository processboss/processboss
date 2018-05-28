package br.com.processboss.core.service;

import br.com.processboss.core.model.User;

public interface IUserService extends IService<User, Long> {

	User findByLogin(String login);
	
}
