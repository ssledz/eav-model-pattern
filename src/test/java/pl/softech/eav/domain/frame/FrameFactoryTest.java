package pl.softech.eav.domain.frame;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
import pl.softech.eav.domain.PersonModelInitializationService;
import pl.softech.eav.domain.attribute.AttributeIdentifier;
import pl.softech.eav.domain.attribute.AttributeRepository;
import pl.softech.eav.domain.category.Category;
import pl.softech.eav.domain.category.CategoryRepository;
import pl.softech.eav.domain.dictionary.DictionaryEntry;
import pl.softech.eav.domain.dictionary.DictionaryEntryRepository;
import pl.softech.eav.domain.frame.FrameFactory;
import pl.softech.eav.domain.object.MyObject;
import pl.softech.eav.domain.object.MyObjectRepository;
import pl.softech.eav.domain.relation.RelationConfiguration;
import pl.softech.eav.domain.relation.RelationConfigurationRepository;
import pl.softech.eav.domain.relation.RelationIdentifier;
import pl.softech.eav.domain.value.BooleanValue;
import pl.softech.eav.domain.value.DictionaryEntryValue;
import pl.softech.eav.domain.value.StringValue;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

/**
 * @author ssledz
 * @since 1.0
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
	private DictionaryEntryRepository dictionaryEntryRepository;
	@Autowired
	private RelationConfigurationRepository relationConfigurationRepository;

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
	public void testAttributes() {

		MyObject object = new MyObject(categoryRepository.findByIdentifier(cmis.getComputerCategory()), "MAUI");

		object.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("model")), new StringValue("Studio15"));

		object.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("os")), new DictionaryEntryValue(
				dictionaryEntryRepository.findByIdentifier(cmis.getWin7())));

		object.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("os")), new DictionaryEntryValue(
				dictionaryEntryRepository.findByIdentifier(cmis.getLinux())));

		object.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("for_sale")), new BooleanValue(false));

		Computer computer = frameFactory.frame(Computer.class, object);

		Assert.assertEquals("Studio15", computer.getModel());

		Assert.assertEquals(new Boolean(false), computer.isForSale());

		Assert.assertNull(computer.getDrive());

		List<String> oses = new LinkedList<>(Collections2.transform(computer.getOs(), new Function<DictionaryEntry, String>() {
			@Override
			public String apply(DictionaryEntry input) {
				return input.getName();
			}
		}));

		Collections.sort(oses);

		Assert.assertEquals(2, oses.size());

		Iterator<String> it = oses.iterator();
		Assert.assertEquals("Linux", it.next());
		Assert.assertEquals("Windows 7", it.next());

		computer.setModel("Studio17");
		Assert.assertEquals("Studio17", computer.getModel());

		Assert.assertNull(computer.getVideo());
		computer.setVideo("Intel Acc");
		Assert.assertEquals("Intel Acc", computer.getVideo());
		computer.setVideo(null);
		Assert.assertNull(computer.getVideo());

		computer.setForSale(true);
		Assert.assertEquals(new Boolean(true), computer.isForSale());

		computer.addOs(dictionaryEntryRepository.findByIdentifier(cmis.getSolaris()));

		oses = new LinkedList<>(Collections2.transform(computer.getOs(), new Function<DictionaryEntry, String>() {
			@Override
			public String apply(DictionaryEntry input) {
				return input.getName();
			}
		}));

		Assert.assertEquals(3, oses.size());
		Collections.sort(oses);
		it = oses.iterator();
		Assert.assertEquals("Linux", it.next());
		Assert.assertEquals("Solaris", it.next());
		Assert.assertEquals("Windows 7", it.next());
	}

	@Test
	@Transactional
	public void testRelations() {

		Category personCategory = categoryRepository.findByIdentifier(pmis.getPersonCategory());

		Category computerCategory = categoryRepository.findByIdentifier(cmis.getComputerCategory());

		relationConfigurationRepository.save(Arrays.asList(//
				new RelationConfiguration(new RelationIdentifier("has_computer"), "Has computer", personCategory, computerCategory),//
				new RelationConfiguration(new RelationIdentifier("has_parent"), "Has parent", personCategory, personCategory),//
				new RelationConfiguration(new RelationIdentifier("has_friend"), "Has friend", personCategory, personCategory)//
				));

		MyObject computerObj = new MyObject(computerCategory, "MAUI");

		Computer computer = frameFactory.frame(Computer.class, computerObj);
		computer.setModel("Studio15");

		MyObject personObj = new MyObject(personCategory, "Slavik");

		personObj.addRelation(relationConfigurationRepository.findByIdentifier(new RelationIdentifier("has_computer")), computerObj);

		Person person = frameFactory.frame(Person.class, personObj);

		Assert.assertNotNull(person.getComputer());
		Assert.assertEquals(computer.getModel(), person.getComputer().getModel());

		MyObject friendTomObj = new MyObject(personCategory, "Tom");

		Person friendTom = frameFactory.frame(Person.class, friendTomObj);
		friendTom.setAge(15);
		friendTom.setFirstname("Tom");
		friendTom.setLastname("Ajax");

		person.addFriend(friendTom);

		MyObject parentObj = new MyObject(personCategory, "Chris");

		Person parent = frameFactory.frame(Person.class, parentObj);
		parent.setAge(51);
		parent.setFirstname("Chris");
		parent.setLastname("Moor");

		person.addFriend(parent);
		person.setParent(parentObj);

		Assert.assertNotNull(person.getFriends());
		Assert.assertEquals(2, person.getFriends().size());
		
		List<Person> friends = Lists.newArrayList(person.getFriends());
		Collections.sort(friends, new Comparator<Person>() {

			@Override
			public int compare(Person o1, Person o2) {
				return o1.getFirstname().compareTo(o2.getFirstname());
			}
		});
		
		Iterator<Person> it = friends.iterator();
		Assert.assertEquals(parent.getFirstname(), it.next().getFirstname());
		Assert.assertEquals(friendTom.getFirstname(), it.next().getFirstname());

		Assert.assertNotNull(person.getParent());
		Assert.assertEquals(parent.getFirstname(), person.getParent().getValueByAttribute(new AttributeIdentifier("firstname")).getValueAsString());
		Assert.assertEquals(parent.getLastname(), person.getParent().getValueByAttribute(new AttributeIdentifier("lastname")).getValueAsString());
		Assert.assertEquals(""+parent.getAge(), person.getParent().getValueByAttribute(new AttributeIdentifier("age")).getValueAsString());
	}

}
