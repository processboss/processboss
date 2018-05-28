package br.com.processboss.core.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Classe que representa a relacao entre um processo
 * e uma tarefa
 */
@Entity
public class ProcessInTask {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private Process process;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="processintask_dependencies")
	private Set<ProcessInTask> dependencies;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="processintask_id")
	private List<ProcessExecutionDetail> executionDetails;
	
	public ProcessInTask() {
		dependencies = new HashSet<ProcessInTask>();
		executionDetails = new ArrayList<ProcessExecutionDetail>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public Set<ProcessInTask> getDependencies() {
		return dependencies;
	}

	public void setDependencies(Set<ProcessInTask> dependencies) {
		this.dependencies = dependencies;
	}
	
	public void addDependency(ProcessInTask dependency){
		this.dependencies.add(dependency);
	}

	public List<ProcessExecutionDetail> getExecutionDetails() {
		return executionDetails;
	}

	public void setExecutionDetails(List<ProcessExecutionDetail> executionDetails) {
		this.executionDetails = executionDetails;
	}
	
	//Debito tecnico por conta do JSF
	public List<ProcessInTask> getListDependencies(){
		List<ProcessInTask> list = new ArrayList<ProcessInTask>();
		for (ProcessInTask processInTask : dependencies) {
			list.add(processInTask);
		}
		return list;
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
		ProcessInTask other = (ProcessInTask) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
