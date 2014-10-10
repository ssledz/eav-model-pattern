package pl.softech.learning.domain.eav.dsl;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.softech.learning.HSqlConfig;
import pl.softech.learning.domain.dictionary.DictionaryEntryRepository;
import pl.softech.learning.domain.dictionary.DictionaryRepository;
import pl.softech.learning.domain.eav.AttributeIdentifier;
import pl.softech.learning.domain.eav.AttributeRepository;
import pl.softech.learning.domain.eav.ComputerModelInitializationService;
import pl.softech.learning.domain.eav.DataTypeSerialisationService;
import pl.softech.learning.domain.eav.MyObject;
import pl.softech.learning.domain.eav.MyObjectRepository;
import pl.softech.learning.domain.eav.category.CategoryRepository;
import pl.softech.learning.domain.eav.frame.FrameFactory;
import pl.softech.learning.domain.eav.frame.Person;
import pl.softech.learning.domain.eav.relation.RelationConfigurationRepository;
import pl.softech.learning.domain.eav.value.ObjectValue;

/**
 * @author ssledz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HSqlConfig.class)
public class CreateModelVisitorTest {

	@Autowired
	private DictionaryRepository dictionaryRepository;

	@Autowired
	private DictionaryEntryRepository dictionaryEntryRepository;

	@Autowired
	private DataTypeSerialisationService dataTypeSerialisationService;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private AttributeRepository attributeRepository;

	@Autowired
	private MyObjectRepository myObjectRepository;
	
	@Autowired
	private RelationConfigurationRepository relationConfigurationRepository;

	@Autowired
	private ComputerModelInitializationService cmis;
	
	@Autowired
	private FrameFactory frameFactory;

	@Before
	public void init() {

		cmis.initializeDict();
	}

	@Test
	@Transactional
	public void test() throws Exception {

		StringBuffer buffer = new StringBuffer();
		System.out.println();
		try (FileReader in = new FileReader(LexerTest.class.getResource("computer.conf").getFile())) {
			try (BufferedReader bin = new BufferedReader(in)) {
				String line;

				while ((line = bin.readLine()) != null) {
					buffer.append(line).append("\n");
				}
			}

		}

		CreateModelVisitor visitor = new CreateModelVisitor(dictionaryRepository, dataTypeSerialisationService);
		Parser p = new Parser(visitor);
		p.parse(buffer.toString());

		categoryRepository.save(visitor.getCategories());
		attributeRepository.save(visitor.getAttributes());
		relationConfigurationRepository.save(visitor.getRelations());
		myObjectRepository.save(visitor.getObjects());

		MyObject obj = myObjectRepository.findByName("MAUI");
		System.out.println(obj);

		ObjectValue makeValue = obj.getValueByAttribute(new AttributeIdentifier("make"));
		Assert.assertEquals("Dell", makeValue.getValueAsString());

		ObjectValue ramValue = obj.getValueByAttribute(new AttributeIdentifier("ram"));
		Assert.assertEquals("4", ramValue.getValueAsString());

		Assert.assertFalse(obj.hasValues(new AttributeIdentifier("ramm")));
		Assert.assertTrue(obj.getValuesByAttribute((new AttributeIdentifier("ramm"))).isEmpty());
		
		Person gyles = frameFactory.frame(Person.class, myObjectRepository.findByName("gyles"));
		Assert.assertEquals("Gyles", gyles.getFirstname());
		Assert.assertEquals("Aitken", gyles.getLastname());
		Assert.assertEquals(22, gyles.getAge().intValue());
		Assert.assertNotNull(gyles.getComputer());
		Assert.assertEquals("320Gb 5400rpm", gyles.getComputer().getDrive());
		Assert.assertEquals(2, gyles.getFriends().size());
		Assert.assertEquals("colton", gyles.getParent().getName());
		

	}

}
