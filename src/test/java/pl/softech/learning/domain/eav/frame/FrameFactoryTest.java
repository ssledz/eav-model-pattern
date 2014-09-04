package pl.softech.learning.domain.eav.frame;

import java.util.Collections;
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

import pl.softech.learning.HSqlConfig;
import pl.softech.learning.domain.dictionary.DictionaryEntry;
import pl.softech.learning.domain.dictionary.DictionaryEntryRepository;
import pl.softech.learning.domain.eav.AttributeIdentifier;
import pl.softech.learning.domain.eav.AttributeRepository;
import pl.softech.learning.domain.eav.ComputerModelInitializationService;
import pl.softech.learning.domain.eav.MyObject;
import pl.softech.learning.domain.eav.MyObjectRepository;
import pl.softech.learning.domain.eav.PersonModelInitializationService;
import pl.softech.learning.domain.eav.category.CategoryRepository;
import pl.softech.learning.domain.eav.value.BooleanValue;
import pl.softech.learning.domain.eav.value.DictionaryEntryValue;
import pl.softech.learning.domain.eav.value.StringValue;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

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
	private DictionaryEntryRepository dictionaryEntryRepository;

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
		
	}

}
