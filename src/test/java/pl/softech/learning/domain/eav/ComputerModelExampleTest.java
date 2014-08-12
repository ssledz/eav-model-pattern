package pl.softech.learning.domain.eav;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.softech.learning.HSqlConfig;
import pl.softech.learning.domain.eav.Attribute.DataType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HSqlConfig.class)
public class ComputerModelExampleTest {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private AttributeRepository attributeRepository;
	@Autowired
	private ObjectValueRepository objectValueRepository;
	@Autowired
	private MyObjectRepository myObjectRepository;

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

		MyObject computer = new MyObject(computerCategory, "MAUI");
		
		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("make")),  new StringValue("Dell"));
		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("model")),  new StringValue("Studio15"));
		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("type")),  new StringValue("Notebook"));
		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("cpu")),  new StringValue("Core 2 Duo 2.4GHz"));
		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("drive")),  new StringValue("320Gb 5400rpm"));
		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("video")),  new StringValue("Dell"));
		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("make")),  new StringValue("Intel Acc"));
		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("ram")),  new IntegerValue(4));
		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("optical")),  new StringValue("DVD RW"));
		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("battery")),  new StringValue("6 cell"));
		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("screen")),  new StringValue("15\""));
		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("os")),  new StringValue("Win7"));
		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("purshase_date")),  new DateValue(new Date()));
		
		myObjectRepository.save(computer);


	}

	@Test
	@Transactional
	public void testExample() {

		MyObject obj = myObjectRepository.findByName("MAUI");
		for(ObjectValue ov : obj.getValues()) {
			System.out.println(ov.toString());
		}
		
	}

}
