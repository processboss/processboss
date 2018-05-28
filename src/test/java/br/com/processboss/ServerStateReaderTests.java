package br.com.processboss;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.processboss.core.bean.ServerState;
import br.com.processboss.core.service.IServerStateService;



@ContextConfiguration(value="classpath:br/com/processboss/tests-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ServerStateReaderTests {

	@Autowired
	private IServerStateService serverStateService;

	@Test
	public void testInit() throws Exception {
		Thread.sleep(120L * 1000L);
	}
	
	@Test
	public void testRead() throws Exception {
		
		while(true){
			ServerState serverState = serverStateService.read();
			assertNotNull(serverState);
//			System.out.println("### " + serverState.getMemory().getUsed() 
//					+ "/" + serverState.getMemory().getTotal()
//					+ " (" + serverState.getMemory().getUsedPercent() + "%)");

			
			System.out.println("### " + serverState.getMemory().getUsed() 
					+ " (" + serverState.getResourcesConsumption().getMemory() + ")");
			
			Thread.sleep(1000);
		}
	}
	
	@Test
	public void testProcess() throws Exception {
		String[] cmd = { "/bin/bash", "-c", "echo $PPID" };
		Process p = Runtime.getRuntime().exec(cmd);
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		int pid = Integer.parseInt(br.readLine());
		System.out.println(pid);
	}


}
