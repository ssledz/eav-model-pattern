package pl.softech.eav.domain.dsl;

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

import pl.softech.eav.HSqlConfig;
import pl.softech.eav.domain.ComputerModelInitializationService;
import pl.softech.eav.domain.attribute.AttributeIdentifier;
import pl.softech.eav.domain.attribute.AttributeRepository;
import pl.softech.eav.domain.attribute.DataTypeSerialisationService;
import pl.softech.eav.domain.category.CategoryRepository;
import pl.softech.eav.domain.dictionary.DictionaryEntryRepository;
import pl.softech.eav.domain.dictionary.DictionaryRepository;
import pl.softech.eav.domain.dsl.CreateModelVisitor;
import pl.softech.eav.domain.dsl.Parser;
import pl.softech.eav.domain.frame.FrameFactory;
import pl.softech.eav.domain.frame.Person;
import pl.softech.eav.domain.object.MyObject;
import pl.softech.eav.domain.object.MyObjectRepository;
import pl.softech.eav.domain.relation.RelationConfigurationRepository;
import pl.softech.eav.domain.value.ObjectValue;

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
