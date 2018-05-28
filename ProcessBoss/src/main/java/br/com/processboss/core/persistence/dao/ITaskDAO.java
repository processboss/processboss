package br.com.processboss.core.persistence.dao;

import java.util.List;

import br.com.processboss.core.model.Process;
import br.com.processboss.core.model.Task;

public interface ITaskDAO extends IDAO<Task, Long> {

	Task loadProcesses(Task task);
	List<Task> findByProcess(Process process);

}
