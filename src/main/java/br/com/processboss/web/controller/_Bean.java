package br.com.processboss.web.controller;

import java.io.Serializable;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public abstract class _Bean implements Serializable {

	private static final long serialVersionUID = -519689694628568590L;
	
	
	public _Bean() {
		Locale.setDefault(new Locale("pt","BR"));
	}

	public Object getJsfParam(String param){
		return getHttpServletRequest().getAttribute(param);
	}
	
	public HttpServletRequest getHttpServletRequest(){
		return (HttpServletRequest)getExternalContext().getRequest();
	}
	
	public HttpServletResponse getHttpServletResponse() {
		return (HttpServletResponse)getExternalContext().getResponse();
	}
	
	public HttpSession getHttpSession(){
		return getHttpServletRequest().getSession();
	}	
	
	public ExternalContext getExternalContext(){
		return getFacesContext().getExternalContext();
	}
	
	public ServletContext getServletContext(){
		return (ServletContext)getExternalContext().getContext();
	}
	
	public String getFileSeparator(){
		return System.getProperty("file.separator");
	}
	
	public FacesContext getFacesContext(){
		return FacesContext.getCurrentInstance();
	}
	
	protected void addMessage(FacesMessage message){
		getFacesContext().addMessage(null, message);
	}
	
}
