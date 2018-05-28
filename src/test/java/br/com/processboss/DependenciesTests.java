package br.com.processboss;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.processboss.core.model.Process;
import br.com.processboss.core.model.ProcessInTask;
import br.com.processboss.core.model.Schedule;
import br.com.processboss.core.model.Task;
import br.com.processboss.core.scheduling.quartz.ScheduleManager;
import br.com.processboss.core.service.IProcessService;
import br.com.processboss.core.service.IScheduleService;
import br.com.processboss.core.service.ITaskService;

@ContextConfiguration(value = "classpath:br/com/processboss/tests-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DependenciesTests {

	private static final Log LOG = LogFactory.getLog(DependenciesTests.class);
	
	@Autowired
	private ScheduleManager scheduleManager;

	@Autowired
	private ITaskService taskService;
	
	@Autowired
	private IScheduleService scheduleService;

	@Autowired
	private IProcessService processService;

	
	@Before
	public void setUp() throws Exception {
		List<Task> all = taskService.listAll();
		for (Task task : all) {
			List<Schedule> schedules = scheduleService.listByTask(task);
			scheduleManager.removeTriggers(schedules);
			taskService.delete(task);
		}
	}

	@Test
	public void testDependencies() throws Exception {
		LOG.debug("--> Criando os processos <--");
		
		Process process1 = new Process();
		process1.setName("Backup ProcessBoss-test");
		process1.setDescription("Realiza o backup da base de dados processboss-test");
		process1.setPath("/home/felipe/scripts/postgres_backup.sh");
		
		Process process2 = new Process();
		process2.setName("Restore processBoss-restore");
		process2.setDescription("Realiza o restore da base de dados processboss-restore");
		process2.setPath("/home/felipe/scripts/postgres_restore.sh");
		
		processService.save(process1);
		processService.save(process2);
		
		LOG.debug("--> Criando a tarefa <--");
		
		Task task1 = new Task();
		task1.setName("Postgres Backup");
		task1.setDescription("Postgres backup");
		
		ProcessInTask pisBackup = new ProcessInTask();
		pisBackup.setProcess(process1);
		
		ProcessInTask pisRestore = new ProcessInTask();
		pisRestore.setProcess(process2);
		
		LOG.debug("--> Definindo as dependencias <--");
		pisRestore.getDependencies().add(pisBackup);
		
		task1.addProcess(pisBackup);
		task1.addProcess(pisRestore);
		
		taskService.save(task1);
		
		LOG.debug("--> Criando o agendamento <--");
		Schedule schedule = new Schedule();
		schedule.setTask(task1);
		schedule.setSeconds("0/20");
		scheduleService.save(schedule);

		scheduleManager.addTrigger(schedule);
		
		LOG.debug("--> Aguarda 60 segundos para que a tarefa seja executada <--");
		Thread.sleep(60L * 1000L);
		
	}

}
