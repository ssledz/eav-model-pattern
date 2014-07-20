package pl.softech.learning.eav.domain;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.softech.learning.HSqlConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HSqlConfig.class)
public class UserRepositoryTest {

	@Autowired
	UserRepository repository;

	@Test
	public void sampleTestCase() {

		User dave = new User("Dave", "Matthews");
		dave = repository.save(dave);

		User carter = new User("Carter", "Beauford");
		carter = repository.save(carter);

		List<User> result = repository.findByLastname("Matthews");

		Assert.assertEquals(1, result.size());
		Assert.assertEquals(dave.getFirstname(), result.get(0).getFirstname());

	}

}
