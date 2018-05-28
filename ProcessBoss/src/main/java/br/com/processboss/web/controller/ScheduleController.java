package br.com.processboss.web.controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;

import br.com.processboss.core.exception.ProcessBossException;
import br.com.processboss.core.model.Schedule;
import br.com.processboss.core.model.Task;
import br.com.processboss.core.scheduling.quartz.ScheduleManager;
import br.com.processboss.core.service.IScheduleService;
import br.com.processboss.core.service.ITaskService;

@ManagedBean(name="scheduleController")
@SessionScoped
public class ScheduleController extends _Bean{

	private static final Log LOGGER = org.apache.commons.logging.LogFactory.getLog(ScheduleController.class);
	
	private static final long serialVersionUID = 8392833176042591878L;

	@ManagedProperty(name="taskService", value="#{taskService}")
	private ITaskService taskService;

	@ManagedProperty(name="scheduleService", value="#{scheduleService}")
	private IScheduleService scheduleService;
	
	@ManagedProperty(name="scheduleManager", value="#{scheduleManager}")
	private ScheduleManager scheduleManager;
	
	private Task entityTask;
	private Schedule entity;
	private int decisionOption;
	
	private boolean mon;
	private boolean tue;
	private boolean wed;
	private boolean thu;
	private boolean fri;
	private boolean sat;
	private boolean sun;
	
	/*
	 * CONSTRUTORES
	 */
	public ScheduleController() {}

	/*
	 * GETS E SETS
	 */
	public Schedule getEntity() {
		return entity;
	}


	public void setEntity(Schedule entity) {
		this.entity = entity;
	}

	public Task getEntityTask() {
		return entityTask;
	}

	public void setEntityTask(Task entityTask) {
		this.entityTask = entityTask;
	}

	public IScheduleService getScheduleService() {
		return scheduleService;
	}
	
	public void setScheduleService(IScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}
	
	public ITaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}

	public int getDecisionOption() {
		return decisionOption;
	}
	
	public void setDecisionOption(int decisionOption) {
		this.decisionOption = decisionOption;
	}
	
	public boolean isMon() {
		return mon;
	}
	public void setMon(boolean mon) {
		this.mon = mon;
	}
	public boolean isTue() {
		return tue;
	}
	public void setTue(boolean tue) {
		this.tue = tue;
	}
	public boolean isWed() {
		return wed;
	}
	
	public void setWed(boolean wed) {
		this.wed = wed;
	}

	public boolean isThu() {
		return thu;
	}

	public void setThu(boolean thu) {
		this.thu = thu;
	}

	public boolean isFri() {
		return fri;
	}

	public void setFri(boolean fri) {
		this.fri = fri;
	}

	public boolean isSat() {
		return sat;
	}

	public void setSat(boolean sat) {
		this.sat = sat;
	}

	public boolean isSun() {
		return sun;
	}

	public void setSun(boolean sun) {
		this.sun = sun;
	}
	
	/*
	 * DESENVOLVIMENTO
	 */
	public String newEntity(){
		entity = new Schedule();
		entityTask = new Task();
		return "newSchedule";
	}
	
	public List<Task> getAllTaskEntities(){
		return taskService.listAll();
	}
	
	public List<Schedule> getAllEntities(){
		return scheduleService.listAll();
	}
	
	public void captureSchedule(ActionEvent action){
		entity = (Schedule)getJsfParam("entity");
	}
	
	public String delete(){
		try {
			if(entity != null){
				scheduleManager.removeTrigger(entity);
				scheduleService.delete(entity);
				addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Agendamento removido com sucesso", ""));
				return "index";
			}else{
				addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nao foi possivel excluir o agendamento", ""));
				return null;
			}
		} catch (ProcessBossException e) {
			addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro ao excluir o agendamento", ""));
			e.printStackTrace();
			return null;
		}
	}
	
	public String cancel(){
		return "index"; 
	}
	
	private String saveOrUpdateSchedule(String minutes, String hours, String dayOfMonth, String month, String dayOfWeek){
		
		try {
			if(entityTask == null){
				addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione uma tarefa", ""));
				return "";
			}
			
			entity.setSeconds("0");
			entity.setMinutes(minutes);
			entity.setHours(hours);
			entity.setDayOfMonth(dayOfMonth);
			entity.setMonth(month);
			entity.setDayOfWeek(dayOfWeek);
			entity.setYear("*");
			entity.setTask(entityTask); 
			
			LOGGER.info("Cron Expression: " + entity.getBuildExpression());
			LOGGER.debug("Cron Expression: " + entity.getBuildExpression());
			
			scheduleService.saveOrUpdate(entity);
			
			scheduleManager.removeTrigger(entity);
			scheduleManager.addTrigger(entity);
			
			addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Tarefa agendada com sucesso", ""));
		
		} catch (ProcessBossException e) {
			addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Ocorreu um erro ao agendar a tarefa.", ""));
			e.printStackTrace();
			return null;
		}
		
		return "index";
	}
	
	public String saveOrUpdateMinutes(){
		return saveOrUpdateSchedule("0/" + entity.getMinutes(), "*", "1/1", "*", "?");
	}

	public String saveOrUpdateHours(){
		if(decisionOption == 1)
			return saveOrUpdateSchedule("0", "0/" + entity.getHours(), "1/1", "*", "?");
		else
			return saveOrUpdateSchedule(entity.getMinutes(), entity.getHours(), "1/1", "*", "?");
	}

	public String saveOrUpdateDaily(){
		if(decisionOption == 1)
			return saveOrUpdateSchedule(entity.getMinutes(), entity.getHours(), "1/" + entity.getDayOfMonth(), "*", "?");
		else
			return saveOrUpdateSchedule(entity.getMinutes(), entity.getHours(), "*", "*", "?");
	}

	public String saveOrUpdateWeekly(){
		StringBuffer week = new StringBuffer();
		if(mon) week.append("MON,");
		if(tue) week.append("TUE,");
		if(wed) week.append("WED,");
		if(thu) week.append("THU,");
		if(fri) week.append("FRI,");
		if(sat) week.append("SAT,");
		if(sun) week.append("SUN,");
		
		return saveOrUpdateSchedule(entity.getMinutes(), entity.getHours(), "?", "*", week.toString());
	}

	public ScheduleManager getScheduleManager() {
		return scheduleManager;
	}

	public void setScheduleManager(ScheduleManager scheduleManager) {
		this.scheduleManager = scheduleManager;
	}
	
}

