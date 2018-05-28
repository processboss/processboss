package br.com.processboss.scheduling;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import br.com.processboss.core.model.Schedule;
import br.com.processboss.core.model.Task;
import br.com.processboss.core.scheduling.quartz.ScheduleManager;
import br.com.processboss.core.service.ITaskService;

@ContextConfiguration(value = "classpath:br/com/processboss/tests-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SchedulingTests {

	@Autowired
	private ITaskService taskService;
	
	@Autowired
	private ScheduleManager scheduleManager;
	
	@Test
	public void testInitialize() throws Exception{
		Thread.sleep(60L * 1000L);
	}
	
	@Test
	public void testAddSchedule() throws Exception{
		
		List<Task> tasks = taskService.listAll();
		
		Assert.notEmpty(tasks, "nenhuma tarefa");
		
		Task task = tasks.get(0);
		
		Schedule schedule = new Schedule();
		schedule.setTask(task);
		
		schedule.setSeconds("0/5");
		
		scheduleManager.addTrigger(schedule);
		
		// Aguarda 15 segundos
		Thread.sleep(15L * 1000L);
		
	}

}
