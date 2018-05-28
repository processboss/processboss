package br.com.processboss.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.processboss.core.model.User;
import br.com.processboss.core.service.IUserService;

@ContextConfiguration(value = "classpath:br/com/processboss/tests-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTests {

	@Autowired
	private IUserService userService;

	@Test
	public void testSaveTask() throws Exception {

		User user = new User();
		user.setName("Administrador");
		user.setLogin("admin");
		user.setPassword("admin");
		user.setAdministrator(true);

		userService.saveOrUpdate(user);

		assertNotNull(user.getId());
	}

}
