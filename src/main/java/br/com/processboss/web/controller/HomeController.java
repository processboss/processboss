package br.com.processboss.web.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="homeCtrl")
@SessionScoped
public class HomeController implements Serializable {

	private static final long serialVersionUID = -3631750213653980322L;
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
