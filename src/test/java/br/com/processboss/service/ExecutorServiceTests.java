package br.com.processboss.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import br.com.processboss.core.exception.ProcessExecutionException;
import br.com.processboss.core.model.Process;
import br.com.processboss.core.model.ProcessInTask;
import br.com.processboss.core.service.IExecutorService;
import br.com.processboss.core.service.IProcessService;

@ContextConfiguration(value = "classpath:br/com/processboss/tests-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ExecutorServiceTests {

	@Autowired
	private IProcessService processService;
	
	@Autowired
	private IExecutorService executorService;

	@Test
	public void testSaveProcess() throws Exception {

		Process process = new Process();

		process.setName("Teste001");
		process.setDescription("Primeiro teste.");
		process.setPath("/home/felipe/scripts/teste001.sh");
		
		processService.save(process);
		
		assertNotNull(process.getId());
	}
	
	@Test
	public void testExecuteProcess() throws Exception {

		Process process = processService.findById(Long.valueOf(7));
		
		ProcessInTask processInTask = new ProcessInTask();
		processInTask.setId(Long.valueOf(1));
		processInTask.setProcess(process);
		
		try {
			executorService.executeProcess(processInTask, "teste", null);
		} catch (ProcessExecutionException e) {
			Assert.isNull(e);
		}
		
		//assertNotNull(process.getId());
	}

}
