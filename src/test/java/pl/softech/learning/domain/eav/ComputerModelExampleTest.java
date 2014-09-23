package pl.softech.learning.domain.eav;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;

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
import pl.softech.learning.domain.eav.value.DateValue;
import pl.softech.learning.domain.eav.value.DictionaryEntryValue;
import pl.softech.learning.domain.eav.value.IntegerValue;
import pl.softech.learning.domain.eav.value.ObjectValue;
import pl.softech.learning.domain.eav.value.ObjectValueRepository;
import pl.softech.learning.domain.eav.value.StringValue;

/**
 * @author ssledz 
 */
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
	@Autowired
	private DictionaryRepository dictionaryRepository;
	@Autowired
	private DictionaryEntryRepository dictionaryEntryRepository;
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

		MyObject computer = new MyObject(categoryRepository.findByIdentifier(cmis.getComputerCategory()), "MAUI");

		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("make")), new DictionaryEntryValue(
				dictionaryEntryRepository.findByIdentifier(cmis.getDell())));
		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("model")), new StringValue("Studio15"));
		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("type")), new DictionaryEntryValue(
				dictionaryEntryRepository.findByIdentifier(cmis.getNotebook())));
		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("cpu")), new StringValue("Core 2 Duo 2.4GHz"));
		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("drive")), new StringValue("320Gb 5400rpm"));
		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("video")), new StringValue("Intel Acc"));
		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("ram")), new IntegerValue(4));
		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("optical")), new StringValue("DVD RW"));
		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("battery")), new StringValue("6 cell"));
		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("screen")), new StringValue("15\""));
		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("os")), new DictionaryEntryValue(
				dictionaryEntryRepository.findByIdentifier(cmis.getWin7())));
		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("os")), new DictionaryEntryValue(
				dictionaryEntryRepository.findByIdentifier(cmis.getLinux())));
		computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("purshase_date")), new DateValue(new Date()));

		myObjectRepository.save(computer);

		MyObject obj = myObjectRepository.findByName("MAUI");

		ObjectValue makeValue = obj.getValueByAttribute(new AttributeIdentifier("make"));
		Assert.assertEquals("Dell", makeValue.getValueAsString());

		ObjectValue ramValue = obj.getValueByAttribute(new AttributeIdentifier("ram"));
		Assert.assertEquals("4", ramValue.getValueAsString());

		Assert.assertFalse(obj.hasValues(new AttributeIdentifier("ramm")));
		Assert.assertTrue(obj.getValuesByAttribute((new AttributeIdentifier("ramm"))).isEmpty());

		Assert.assertNull(obj.getValueByAttribute((new AttributeIdentifier("ramm"))));

		Set<ObjectValue> opticals = obj.getValuesByAttribute(new AttributeIdentifier("optical"));
		Assert.assertEquals(1, opticals.size());
		Assert.assertEquals("DVD RW", opticals.iterator().next().getValueAsString());

		Set<ObjectValue> oss = obj.getValuesByAttribute(new AttributeIdentifier("os"));
		Assert.assertEquals(2, oss.size());

		ObjectValue[] arr = oss.toArray(new ObjectValue[2]);
		Arrays.sort(arr, new Comparator<ObjectValue>() {

			@Override
			public int compare(ObjectValue o1, ObjectValue o2) {
				return o1.getValueAsString().compareTo(o2.getValueAsString());
			}
		});
		Assert.assertEquals("Linux", arr[0].getValueAsString());
		Assert.assertEquals("Windows 7", arr[1].getValueAsString());

	}

	@Test
	@Transactional
	public void testCategoryConstraint() {
		MyObject computer = new MyObject(categoryRepository.findByIdentifier(cmis.getComputerCategory()), "PING");

		try {
			computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("firstname")), new StringValue("Slawek"));
			Assert.fail();
		} catch (Exception e) {
		}

	}

	@Test
	@Transactional
	public void testValueMatchAttributeConstraint() {
		MyObject computer = new MyObject(categoryRepository.findByIdentifier(cmis.getComputerCategory()), "PONG");

		try {
			computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("make")), new StringValue("Dell"));
			Assert.fail();
		} catch (Exception e) {
		}

		try {
			computer.addValue(attributeRepository.findByIdentifier(new AttributeIdentifier("make")), new DictionaryEntryValue(
					dictionaryEntryRepository.findByIdentifier(cmis.getNotebook())));
			Assert.fail();
		} catch (Exception e) {
		}

	}

}
