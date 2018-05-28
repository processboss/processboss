package br.com.processboss.web.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.commons.lang.StringUtils;

import br.com.processboss.core.model.User;
import br.com.processboss.core.service.IUserService;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean extends _Bean implements Serializable {

	private static final long serialVersionUID = 7316183441445886925L;

	@ManagedProperty(name = "userService", value = "#{userService}")
	private IUserService userService;

	private String username;
	private String password;

	private User user;

	public LoginBean() {
	}

	public String login() {
		if(StringUtils.isEmpty(username)){
			addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informe o login!", ""));
			return "login";
		}
		if(StringUtils.isEmpty(password)){
			addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informe a senha!", ""));
			return "login";
		}
		
		user = userService.findByLogin(username);
		
		if (user == null) {
			addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login!", ""));
			return "login";
		}
		
		if (!password.equals(user.getPassword())) {
			addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid password!", ""));
			user = null;
			return "login";
		}
		
		return "dashboard/index";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
