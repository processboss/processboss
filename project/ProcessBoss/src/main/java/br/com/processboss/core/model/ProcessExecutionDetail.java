package br.com.processboss.core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class ProcessExecutionDetail implements Serializable {

	private static final long serialVersionUID = 1847636407064474065L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="exec_start")
	private Date start;
	
	@Column(name="exec_end")
	private Date end;
	
	private double memoryMean;
	private double memoryTop;
	private double cpuMean;
	private double cpuTop;
	
	@Transient
	private double memoryTotal;
	@Transient
	private double cpuTotal;
	@Transient
	private long count;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private ProcessInTask processInTask;
	
	private ExecutionStatus status;

	public ProcessExecutionDetail() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public double getMemoryMean() {
		return memoryMean;
	}

	public void setMemoryMean(double memoryMean) {
		this.memoryMean = memoryMean;
	}

	public double getMemoryTop() {
		return memoryTop;
	}

	public void setMemoryTop(double memoryTop) {
		this.memoryTop = memoryTop;
	}

	public double getCpuMean() {
		return cpuMean;
	}

	public void setCpuMean(double cpuMean) {
		this.cpuMean = cpuMean;
	}

	public double getCpuTop() {
		return cpuTop;
	}

	public void setCpuTop(double cpuTop) {
		this.cpuTop = cpuTop;
	}

	public ProcessInTask getProcessInTask() {
		return processInTask;
	}

	public void setProcessInTask(ProcessInTask processInTask) {
		this.processInTask = processInTask;
	}

	public ExecutionStatus getStatus() {
		return status;
	}

	public void setStatus(ExecutionStatus status) {
		this.status = status;
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
		ProcessExecutionDetail other = (ProcessExecutionDetail) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void add(double memory, double cpu){
		count++;
		memoryTotal+=memory;
		cpuTotal+=cpu;
		
		memoryMean = memoryTotal / count;
		cpuMean = cpuTotal / count;
		
		if(memory > memoryTop){
			memoryTop = memory;
		}
		
		if(cpu > cpuTop){
			cpuTop = cpu;
		}
	}

	@Override
	public String toString() {
		return "ProcessExecutionDetail [start=" + start + ", end=" + end
				+ ", memoryMean=" + memoryMean + ", memoryTop=" + memoryTop
				+ ", cpuMean=" + cpuMean + ", cpuTop=" + cpuTop + "]";
	}
}
