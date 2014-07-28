package pl.softech.learning.domain.eav;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.softech.learning.HSqlConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HSqlConfig.class)
public class ComputerModelExampleTest {

	@Autowired
	private CategoryRepository categoryRepository;

	@Before
	public void init() {

		categoryRepository.save(new Category(new CategoryIdentifier("computer"), "Computer"));

	}
	
	@Test
	public void testExample() {
		
	}

}
