package pl.softech.eav.domain;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.softech.eav.HSqlConfig;
import pl.softech.eav.domain.attribute.AttributeIdentifier;
import pl.softech.eav.domain.attribute.AttributeRepository;
import pl.softech.eav.domain.category.CategoryRepository;
import pl.softech.eav.domain.dictionary.DictionaryEntryRepository;
import pl.softech.eav.domain.dictionary.DictionaryRepository;
import pl.softech.eav.domain.object.MyObject;
import pl.softech.eav.domain.object.MyObjectRepository;
import pl.softech.eav.domain.relation.RelationConfiguration;
import pl.softech.eav.domain.relation.RelationConfigurationRepository;
import pl.softech.eav.domain.relation.RelationIdentifier;
import pl.softech.eav.domain.value.DictionaryEntryValue;
import pl.softech.eav.domain.value.ObjectValueRepository;
import pl.softech.eav.domain.value.StringValue;

/**
 * @author ssledz
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HSqlConfig.class)
public class PersonComputerRelationTest {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private AttributeRepository attributeRepository;
	@Autowired
	private ObjectValueRepository objectValueRepository;
	@Autowired
	private MyObjectRepository myObjectRepository;
	@Autowired
	private DictionaryRepository dictionaryRepository;
	@Autowired
	private DictionaryEntryRepository dictionaryEntryRepository;
	@Autowired
	private RelationConfigurationRepository relationConfigurationRepository;
	@Autowired
	private ComputerModelInitializationService cmis;
	@Autowired
	private PersonModelInitializationService pmis;

	@Before
	public void init() {

		cmis.initialize();

		pmis.initialize();

	}

	@Test
	@Transactional
	public void testExample() {

		MyObject computer = new MyObject(categoryRepository.findByIdentifier(cmis.getComputerCategory()), "STAR");

		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("make")), new DictionaryEntryValue(
				dictionaryEntryRepository.findByIdentifier(cmis.getDell())));

		MyObject computer2 = new MyObject(categoryRepository.findByIdentifier(cmis.getComputerCategory()), "STAR2");

		MyObject person = new MyObject(categoryRepository.findByIdentifier(pmis.getPersonCategory()), "Slavik");
		person.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("firstname")), new StringValue("Slawomir"));
		person.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("lastname")), new StringValue("Sledz"));

		MyObject person2 = new MyObject(categoryRepository.findByIdentifier(pmis.getPersonCategory()), "Slavik2");
		
		myObjectRepository.save(Arrays.asList(computer, computer2, person, person2));

		RelationConfiguration hasComputer = new RelationConfiguration(new RelationIdentifier("has_computer"), "has",
				categoryRepository.findByIdentifier(pmis.getPersonCategory()), categoryRepository.findByIdentifier(cmis
						.getComputerCategory()));

		relationConfigurationRepository.save(hasComputer);

		person.addRelation(hasComputer, computer);

		myObjectRepository.save(person);
		
		try {
			person.addRelation(hasComputer, person2);
			Assert.fail();
		}catch(Exception e) {
			
		}

	}
}
