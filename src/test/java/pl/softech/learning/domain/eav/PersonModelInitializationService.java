package pl.softech.learning.domain.eav;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.softech.learning.domain.dictionary.DictionaryEntryRepository;
import pl.softech.learning.domain.dictionary.DictionaryRepository;
import pl.softech.learning.domain.eav.DataType.Type;
import pl.softech.learning.domain.eav.category.Category;
import pl.softech.learning.domain.eav.category.CategoryIdentifier;
import pl.softech.learning.domain.eav.category.CategoryRepository;
import pl.softech.learning.domain.eav.value.ObjectValueRepository;

@Service
public class PersonModelInitializationService {

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

	private CategoryIdentifier personCategory;

	public CategoryIdentifier getPersonCategory() {
		return personCategory;
	}

	@Transactional
	public void initialize() {

		personCategory = new CategoryIdentifier("person");
		Category person = new Category(personCategory, "Person");
		categoryRepository.save(person);

		attributeRepository.save(new Attribute(new AttributeIdentifier("firstname"), "First Name", person, new DataType(Type.TEXT)));
		attributeRepository.save(new Attribute(new AttributeIdentifier("lastname"), "Last Name", person, new DataType(Type.TEXT)));
		attributeRepository.save(new Attribute(new AttributeIdentifier("age"), "Age", person, new DataType(Type.INTEGER)));
	}

}
