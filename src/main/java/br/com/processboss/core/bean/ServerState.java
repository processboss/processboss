package br.com.processboss.core.bean;

import java.io.Serializable;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;

/**
 * Classe que contem as informacoes atuais do servidor
 * 
 */
public class ServerState implements Serializable {

	private static final long serialVersionUID = -6744252510761261747L;

	private Mem memory;
	private CpuInfo[] cpuInfo;
	private CpuPerc cpuPerc;
	private Resources resourcesConsumption;

	public ServerState() {
	}

	public Mem getMemory() {
		return memory;
	}

	public void setMemory(Mem memory) {
		this.memory = memory;
	}

	public CpuInfo[] getCpuInfo() {
		return cpuInfo;
	}

	public void setCpuInfo(CpuInfo[] cpuInfo) {
		this.cpuInfo = cpuInfo;
	}
	
	public double getCpuFree(){
		return this.cpuPerc.getIdle() * 100;
	}
	
	public double getCpuUsed(){
		return this.cpuPerc.getCombined() * 100;
	}

	public CpuPerc getCpuPerc() {
		return cpuPerc;
	}

	public void setCpuPerc(CpuPerc cpuPerc) {
		this.cpuPerc = cpuPerc;
	}

	public Resources getResourcesConsumption() {
		return resourcesConsumption;
	}

	public void setResourcesConsumption(Resources resourcesConsumption) {
		this.resourcesConsumption = resourcesConsumption;
	}
}
