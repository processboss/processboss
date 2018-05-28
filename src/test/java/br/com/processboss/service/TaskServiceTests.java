package br.com.processboss.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.processboss.core.model.Task;
import br.com.processboss.core.service.ITaskService;

@ContextConfiguration(value = "classpath:br/com/processboss/tests-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TaskServiceTests {

	@Autowired
	private ITaskService taskService;

	@Test
	public void testSaveTask() throws Exception {

		Task task = new Task();
		task.setName("Task1");
		task.setDescription("Task to be executed");

		taskService.save(task);

		assertNotNull(task.getId());
	}

}
