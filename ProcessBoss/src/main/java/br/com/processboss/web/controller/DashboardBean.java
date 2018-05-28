package br.com.processboss.web.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.commons.lang.time.DateUtils;
import org.hyperic.sigar.CpuPerc;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.processboss.core.bean.ServerState;
import br.com.processboss.core.service.IServerStateService;
import br.com.processboss.core.model.ProcessInTask;

@ManagedBean(name="dashboardBean")
@SessionScoped
public class DashboardBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(name="serverStateService", value="#{serverStateService}")
	private IServerStateService serverStateService;
	
	private CartesianChartModel categoryModel;  

	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	
	private Map<Long, Double> memoryHistory = new LinkedHashMap<Long, Double>();
	private Map<Long, Double> cpuHistory = new LinkedHashMap<Long, Double>();
  
    public DashboardBean() {  
    	categoryModel = new CartesianChartModel();  
    }  
  
    public CartesianChartModel getServerState() {  
    	categoryModel.clear();
    	
    	Date now = new Date();
    	ServerState serverState = serverStateService.read();
    	
    	Date remove = DateUtils.addSeconds(now, -30);

    	memoryHistory.remove(sdf.format(remove));
    	memoryHistory.put(now.getTime(), serverState.getMemory().getUsedPercent());
    	
    	ChartSeries memory = new ChartSeries(); 
        memory.setLabel("Memory");  
        
        Iterator<Entry<Long, Double>> iterator = memoryHistory.entrySet().iterator();
        while(iterator.hasNext()){
        	Entry<Long, Double> entry = iterator.next();
        	
        	Long key = entry.getKey();
			if(key <= remove.getTime()){
				continue;
			}
			memory.set(sdf.format(new Date(key)), entry.getValue());
		}
        categoryModel.addSeries(memory); 
        
        
        CpuPerc cpuPerc = serverState.getCpuPerc();
		cpuHistory.put(now.getTime(), cpuPerc.getCombined());
    	
    	ChartSeries cpu = new ChartSeries(); 
    	cpu.setLabel("CPU");  
    	iterator = cpuHistory.entrySet().iterator();
        while(iterator.hasNext()){
        	Entry<Long, Double> entry = iterator.next();
        	
        	Long key = entry.getKey();
			if(key <= remove.getTime()){
				continue;
			}
			cpu.set(sdf.format(new Date(key)), entry.getValue());
		}
        categoryModel.addSeries(cpu); 
        
        return categoryModel;  
    }  
  
    public List<ProcessInTask> getActiveProcesses(){
    	return serverStateService.getInProgressProcess();
    }
    
    public List<ProcessInTask> getWaitProcesses(){
    	return serverStateService.getWaitingProcess();
    }
    
	public IServerStateService getServerStateService() {
		return serverStateService;
	}

	public void setServerStateService(IServerStateService serverStateService) {
		this.serverStateService = serverStateService;
	}  
}
