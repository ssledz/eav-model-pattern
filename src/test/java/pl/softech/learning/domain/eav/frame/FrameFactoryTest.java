package pl.softech.learning.domain.eav.frame;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.softech.learning.HSqlConfig;
import pl.softech.learning.domain.eav.AttributeIdentifier;
import pl.softech.learning.domain.eav.AttributeRepository;
import pl.softech.learning.domain.eav.ComputerModelInitializationService;
import pl.softech.learning.domain.eav.MyObject;
import pl.softech.learning.domain.eav.MyObjectRepository;
import pl.softech.learning.domain.eav.PersonModelInitializationService;
import pl.softech.learning.domain.eav.category.CategoryRepository;
import pl.softech.learning.domain.eav.value.StringValue;

/**
 * @author ssledz 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HSqlConfig.class)
public class FrameFactoryTest {

	@Autowired
	private ComputerModelInitializationService cmis;
	@Autowired
	private PersonModelInitializationService pmis;
	@Autowired
	private MyObjectRepository objectRepository;
	@Autowired
	private AttributeRepository attributeRepository;

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private FrameFactory frameFactory;

	@Before
	public void init() {

		cmis.initialize();

		pmis.initialize();

	}

	@Test
	@Transactional
	public void test() {

		MyObject object = new MyObject(categoryRepository.findByIdentifier(cmis.getComputerCategory()), "MAUI");
		object.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("model")), new StringValue("Studio15"));
		
		Computer computer = frameFactory.frame(Computer.class, object);
		System.out.println(computer.getModel());
	}

}
