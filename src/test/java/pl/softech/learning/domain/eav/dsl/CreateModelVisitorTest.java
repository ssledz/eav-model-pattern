package pl.softech.learning.domain.eav.dsl;

import java.io.BufferedReader;
import java.io.FileReader;
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
import pl.softech.learning.domain.dictionary.Dictionary;
import pl.softech.learning.domain.dictionary.DictionaryEntry;
import pl.softech.learning.domain.dictionary.DictionaryEntryIdentifier;
import pl.softech.learning.domain.dictionary.DictionaryEntryRepository;
import pl.softech.learning.domain.dictionary.DictionaryIdentifier;
import pl.softech.learning.domain.dictionary.DictionaryRepository;
import pl.softech.learning.domain.eav.AttributeIdentifier;
import pl.softech.learning.domain.eav.AttributeRepository;
import pl.softech.learning.domain.eav.CategoryRepository;
import pl.softech.learning.domain.eav.DataTypeSerialisationService;
import pl.softech.learning.domain.eav.MyObject;
import pl.softech.learning.domain.eav.MyObjectRepository;
import pl.softech.learning.domain.eav.ObjectValue;

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

	private DictionaryIdentifier computerType;
	private DictionaryIdentifier os;
	private DictionaryIdentifier computerMake;
	private DictionaryEntryIdentifier notebook;
	private DictionaryEntryIdentifier desktop;
	private DictionaryEntryIdentifier win7;
	private DictionaryEntryIdentifier win8;
	private DictionaryEntryIdentifier linux;
	private DictionaryEntryIdentifier solaris;
	private DictionaryEntryIdentifier dell;
	private DictionaryEntryIdentifier lenovo;
	private DictionaryEntryIdentifier apple;

	@Before
	public void init() {

		computerType = new DictionaryIdentifier("computer_type");
		os = new DictionaryIdentifier("os");
		computerMake = new DictionaryIdentifier("computer_make");

		dictionaryRepository.save(Arrays.asList(//
				new Dictionary(computerType, "Computer Type"),//
				new Dictionary(os, "Operating System"),//
				new Dictionary(computerMake, "Computer Make")//
				));

		notebook = new DictionaryEntryIdentifier("notebook");
		desktop = new DictionaryEntryIdentifier("desktop");
		win7 = new DictionaryEntryIdentifier("win7");
		win8 = new DictionaryEntryIdentifier("win8");
		linux = new DictionaryEntryIdentifier("linux");
		solaris = new DictionaryEntryIdentifier("solaris");
		dell = new DictionaryEntryIdentifier("dell");
		lenovo = new DictionaryEntryIdentifier("lenovo");
		apple = new DictionaryEntryIdentifier("apple");

		dictionaryEntryRepository.save(Arrays.asList(//
				new DictionaryEntry(dictionaryRepository.findByIdentifier(computerType), notebook, "Notebook"),//
				new DictionaryEntry(dictionaryRepository.findByIdentifier(computerType), desktop, "Desktop"),//
				new DictionaryEntry(dictionaryRepository.findByIdentifier(os), win7, "Windows 7"),//
				new DictionaryEntry(dictionaryRepository.findByIdentifier(os), win8, "Window 8"),//
				new DictionaryEntry(dictionaryRepository.findByIdentifier(os), linux, "Linux"),//
				new DictionaryEntry(dictionaryRepository.findByIdentifier(os), solaris, "Solaris"),//
				new DictionaryEntry(dictionaryRepository.findByIdentifier(computerMake), dell, "Dell"),//
				new DictionaryEntry(dictionaryRepository.findByIdentifier(computerMake), lenovo, "Lenovo"),//
				new DictionaryEntry(dictionaryRepository.findByIdentifier(computerMake), apple, "Apple")//
				));
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
		myObjectRepository.save(visitor.getObjects());
		
		MyObject obj = myObjectRepository.findByName("MAUI");
		System.out.println(obj);

		ObjectValue makeValue = obj.getValueByAttribute(new AttributeIdentifier("make"));
		Assert.assertEquals("Dell", makeValue.getValueAsString());

		ObjectValue ramValue = obj.getValueByAttribute(new AttributeIdentifier("ram"));
		Assert.assertEquals("4", ramValue.getValueAsString());

		Assert.assertFalse(obj.hasValues(new AttributeIdentifier("ramm")));
		Assert.assertTrue(obj.getValuesByAttribute((new AttributeIdentifier("ramm"))).isEmpty());
		
		
		
		
	}

}
