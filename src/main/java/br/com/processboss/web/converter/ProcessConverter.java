package br.com.processboss.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import br.com.processboss.core.model.Process;
import br.com.processboss.core.persistence.dao.impl.ProcessDAO;

@FacesConverter(forClass=Process.class, value="processConverter")
public class ProcessConverter implements Converter {

	
	@Override
	public Object getAsObject(FacesContext ctx, UIComponent comp, String str) {
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext());
		ProcessDAO dao = context.getBean(ProcessDAO.class);
		Process p = dao.findById(Long.parseLong(str));
		return p;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent comp, Object obj) {
		Process process = (Process)obj;
		return String.valueOf(process.getId());
	}

}
