package br.com.processboss.core.persistence.dao;

import java.util.List;

import br.com.processboss.core.model.ProcessExecutionDetail;
import br.com.processboss.core.model.ProcessInTask;


public interface IProcessExecutionDetailDAO extends IDAO<ProcessExecutionDetail, Long> {

	List<ProcessExecutionDetail> listByProcessInTask(ProcessInTask processInTask);
	void deleteByProcessInTask(ProcessInTask processInTask);
	List<ProcessExecutionDetail> getHistory(ProcessInTask process, int limit);

}