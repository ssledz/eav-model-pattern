package pl.softech.learning.domain.eav;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.softech.learning.HSqlConfig;
import pl.softech.learning.domain.eav.Attribute.DataType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HSqlConfig.class)
public class ComputerModelExampleTest {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private AttributeRepository attributeRepository;

	@Before
	public void init() {

		Category computerCategory = new Category(new CategoryIdentifier("computer"), "Computer");
		categoryRepository.save(computerCategory);

		attributeRepository.save(new Attribute(new AttributeIdentifier("make"), "Make", computerCategory, DataType.TEXT));
		attributeRepository.save(new Attribute(new AttributeIdentifier("model"), "Model", computerCategory, DataType.TEXT));
		attributeRepository.save(new Attribute(new AttributeIdentifier("type"), "Type", computerCategory, DataType.TEXT));
		attributeRepository.save(new Attribute(new AttributeIdentifier("cpu"), "CPU", computerCategory, DataType.TEXT));
		attributeRepository.save(new Attribute(new AttributeIdentifier("drive"), "Drive", computerCategory, DataType.TEXT));
		attributeRepository.save(new Attribute(new AttributeIdentifier("video"), "Video", computerCategory, DataType.TEXT));
		attributeRepository.save(new Attribute(new AttributeIdentifier("ram"), "RAM (GB)", computerCategory, DataType.INTEGER));
		attributeRepository.save(new Attribute(new AttributeIdentifier("optical"), "Optical", computerCategory, DataType.TEXT));
		attributeRepository.save(new Attribute(new AttributeIdentifier("battery"), "Battery", computerCategory, DataType.TEXT));
		attributeRepository.save(new Attribute(new AttributeIdentifier("screen"), "Screen", computerCategory, DataType.TEXT));
		attributeRepository.save(new Attribute(new AttributeIdentifier("os"), "OS", computerCategory, DataType.TEXT));
		attributeRepository.save(new Attribute(new AttributeIdentifier("purshase_date"), "Purschase Date", computerCategory, DataType.DATE));

	}

	@Test
	public void testExample() {

	}

}
