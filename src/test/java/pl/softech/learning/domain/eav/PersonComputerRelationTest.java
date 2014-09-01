package pl.softech.learning.domain.eav;

import java.util.Arrays;

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
import pl.softech.learning.domain.eav.category.CategoryRepository;
import pl.softech.learning.domain.eav.relation.RelationConfiguration;
import pl.softech.learning.domain.eav.relation.RelationConfigurationRepository;
import pl.softech.learning.domain.eav.relation.RelationIdentifier;
import pl.softech.learning.domain.eav.value.DictionaryEntryValue;
import pl.softech.learning.domain.eav.value.ObjectValueRepository;
import pl.softech.learning.domain.eav.value.StringValue;

/**
 * @author ssledz 
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
