package br.com.processboss.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.processboss.core.model.Process;
import br.com.processboss.core.model.ProcessInTask;
import br.com.processboss.core.model.Schedule;
import br.com.processboss.core.model.Task;
import br.com.processboss.core.service.IProcessService;
import br.com.processboss.core.service.ITaskService;

@ContextConfiguration(value = "classpath:br/com/processboss/tests-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ScheduleTests {

	@Autowired
	private IProcessService processService;
	
	@Autowired
	private ITaskService taskService;

	@Test
	public void testSaveProcess() throws Exception {

		Process process = new Process();

		process.setName("TesteSchedule");
		process.setDescription("Teste de Schedule.");
		process.setPath("/tmp/processboss/simulador1.sh");
		
		processService.save(process);
		
		assertNotNull(process.getId());
		
		ProcessInTask processInTask = new ProcessInTask();
		processInTask.setProcess(process);

		Task task = new Task();
		task.setName("Tarefa001");
		task.setDescription("Uma tarefa simples");
		task.addProcess(processInTask);
		
		taskService.save(task);
		
		assertNotNull(task.getId());
		
		Schedule schedule = new Schedule();
		schedule.setMinutes("1");
		schedule.setTask(task);
	}
	

}
