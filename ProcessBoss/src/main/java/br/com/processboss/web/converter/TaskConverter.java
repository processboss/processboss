package br.com.processboss.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import br.com.processboss.core.model.Task;
import br.com.processboss.core.persistence.dao.impl.TaskDAO;


@FacesConverter(forClass=Process.class, value="taskConverter")
public class TaskConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent comp, String str) {
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext());
		TaskDAO dao = context.getBean(TaskDAO.class);
		Task p = dao.findById(Long.parseLong(str));
		return p;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent comp, Object obj) {
		Task task = (Task)obj;
		return String.valueOf(task.getId());
	}

	
	
}
