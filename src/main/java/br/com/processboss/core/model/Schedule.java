package br.com.processboss.core.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.apache.commons.lang.StringUtils;

import br.com.processboss.core.util.CronConverterUtil;

/**
 * Classe que representa um agendamento
 */
@Entity
public class Schedule implements Serializable {

	private static final long serialVersionUID = -7406144889080599290L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String seconds;
	private String minutes;
	private String hours;
	
	private String dayOfMonth;
	private String month;
	private String dayOfWeek;
	private String year;
	
	@OneToOne(targetEntity=Task.class)
	private Task task;
	
	public Schedule() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSeconds() {
		return seconds;
	}

	public void setSeconds(String seconds) {
		this.seconds = seconds;
	}

	public String getMinutes() {
		return minutes;
	}

	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getDayOfMonth() {
		return dayOfMonth;
	}

	public void setDayOfMonth(String dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schedule other = (Schedule) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * Monta a expressao para o agendamento
	 * @return
	 */
	public String buildExpression(){
		StringBuilder expr = new StringBuilder();
		
		expr.append(StringUtils.defaultIfEmpty(seconds, "*")).append(" ");
		expr.append(StringUtils.defaultIfEmpty(minutes, "*")).append(" ");
		expr.append(StringUtils.defaultIfEmpty(hours, "*")).append(" ");
		expr.append(StringUtils.defaultIfEmpty(dayOfMonth, "*")).append(" ");
		expr.append(StringUtils.defaultIfEmpty(month, "*")).append(" ");
		expr.append(StringUtils.defaultIfEmpty(dayOfWeek, "?")).append(" ");
		expr.append(StringUtils.defaultIfEmpty(year, "*")).append(" ");
		
		return expr.toString();
	}
	
	/**
	 * Metodo criado apenas para atender ao padrão reconhecido pelo Primefaces 
	 */
	public String getBuildExpression(){
		return CronConverterUtil.converterToText(buildExpression());
	}
	
}
