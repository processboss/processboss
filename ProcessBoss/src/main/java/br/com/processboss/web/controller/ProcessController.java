package br.com.processboss.web.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.processboss.core.model.Process;
import br.com.processboss.core.service.IProcessService;

@ManagedBean(name="processController")
@SessionScoped
public class ProcessController extends _Bean implements Serializable {

	private static final long serialVersionUID = -3097963894781724638L;
	
	@ManagedProperty(name="processService", value="#{processService}")
	private IProcessService processService;
	
	private UploadedFile file;
	
	private Process entity;
	
	/*
	 * CONSTRUTORES
	 */
	public ProcessController() {}

	/*
	 * GETS E SETS
	 */
	public IProcessService getProcessService() {
		return processService;
	}

	public void setProcessService(IProcessService processService) {
		this.processService = processService;
	}

	public Process getEntity() {
		return entity;
	}

	public void setEntity(Process entity) {
		this.entity = entity;
	}
	
	public UploadedFile getFile() {  
        return file;  
    }  
	  
    public void setFile(UploadedFile file) {  
        this.file = file;  
    }
	
	/*
	 * DESENVOLVIMENTO
	 */
	public List<Process> getAllEntities(){
		return processService.listAll();
	}
	
	public String updateEntity(){
		entity = (Process)getJsfParam("entity");
		return "updateProcess";
	}

	public String newEntity(){
		entity = new Process();
		return "newProcess";
	}
	
	public String cancel(){
		return "index"; 
	}
	
	public String saveOrUpdate(){

		File existentFile = new File(entity.getPath());
		boolean pathExists = existentFile.exists();
		
		//valida se um arquivo está sendo enviado, ou se existe o arquivo no caminho selecionado
		if(file == null && pathExists == false){
			addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nao foi possivel inserir/alterar o processo, pois e preciso anexar um arquivo ou apontar para um existente.", ""));
			return null;
		}
		
		if(existentFile != null && entity.getPath().equals("")){
			addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Caminho é obrigatório.", ""));
			return null;
		}
		
		if(entity != null){
			
			if(file != null){
				try {
					
					String newPath = entity.getPath();
					
					byte[] conteudo = file.getContents();
					FileOutputStream fos = new FileOutputStream(newPath);
					fos.write(conteudo);
					fos.close();
					
				} catch (FileNotFoundException e) {
					addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nao foi possivel inserir/alterar o processo. O arquivo anexado esta corrompido.", ""));
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nao foi possivel inserir/alterar o processo. O arquivo anexado esta corrompido.", ""));
					e.printStackTrace();
					return null;
				}
			}
			
			processService.saveOrUpdate(entity);
			addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Processo inserido/alterado com suscesso.", ""));
			return "index";
		}else{
			addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Houve um erro ao tentar inserir/alterar um processo.", ""));
			return null;
		}
	}
	
	public void captureProcess(ActionEvent action){
		entity = (Process)getJsfParam("entity");
	}
	
	public String delete(){
		if(entity != null){
			processService.delete(entity);
			addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Processo excluido com suscesso.", ""));
			return "index";
		}else{
			addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Houve um erro ao tentar excluir um processo.", ""));
			return null;
		}
	}
	
    public void handleFileUpload(FileUploadEvent event){  
        if(event.getFile() != null){  
        	file = event.getFile();  
        }  
    } 

    public String loadDetails(){
    	entity = (Process)getJsfParam("entity");
    	return "processDetails";
    }
    
}
