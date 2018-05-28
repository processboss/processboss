package br.com.processboss.core.bean;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import br.com.processboss.core.model.ProcessExecutionDetail;

public class Resources {
	
	public static final double MARGIN = 5.0D;
	public static final double MAX_CPU = 90.0D;

	private Double memory = 0.0D;
	private Double cpu = 0.0D;
	
	public Resources(double memory, double cpu) {
		this.memory = memory;
		this.cpu = cpu;
	}
	
	public Resources(List<ProcessExecutionDetail> details) {
		
		if(!CollectionUtils.isEmpty(details)){
			
			Double maxMemory = null;
			Double minMemory = null;
			Double maxCpu = null;
			Double minCpu = null;
			
			double totalMemory = 0.0D;
			double totalCpu = 0.0D;
			
			for (ProcessExecutionDetail detail : details) {
				
				double memory = detail.getMemoryMean();
				
				if(maxMemory == null || memory > maxMemory){
					totalMemory += (maxMemory == null ? 0.0D : maxMemory);
					maxMemory = memory;
				}
				
				if(minMemory == null || memory < minMemory){
					totalMemory += (minMemory == null ? 0.0D : minMemory);
					minMemory = memory;
				}
				
				double cpu = detail.getCpuMean();
				
				if(maxCpu == null || cpu > maxCpu){
					totalCpu += (maxCpu == null ? 0.0D : maxCpu);
					maxCpu = cpu;
				}
				
				if(minCpu == null || cpu < minCpu){
					totalCpu += (minCpu == null ? 0.0D : minCpu);
					minCpu = cpu;
				}
				
			}
			
			this.memory = addMargin(totalMemory / details.size());
			this.cpu = addMargin(totalCpu / details.size());
		}
	}

	public Double getMemory() {
		return memory;
	}

	public Double getCpu() {
		return cpu;
	}
	
	public static double addMargin(double value){
		return value * (1 + (MARGIN / 100));
	}
}
