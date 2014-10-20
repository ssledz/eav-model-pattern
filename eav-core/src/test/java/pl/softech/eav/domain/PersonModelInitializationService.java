/*
 * Copyright 2013 Sławomir Śledź <slawomir.sledz@sof-tech.pl>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.softech.eav.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.softech.eav.domain.attribute.Attribute;
import pl.softech.eav.domain.attribute.AttributeIdentifier;
import pl.softech.eav.domain.attribute.AttributeRepository;
import pl.softech.eav.domain.attribute.DataType;
import pl.softech.eav.domain.attribute.DataType.Type;
import pl.softech.eav.domain.category.Category;
import pl.softech.eav.domain.category.CategoryIdentifier;
import pl.softech.eav.domain.category.CategoryRepository;
import pl.softech.eav.domain.dictionary.DictionaryEntryRepository;
import pl.softech.eav.domain.dictionary.DictionaryRepository;
import pl.softech.eav.domain.object.MyObjectRepository;
import pl.softech.eav.domain.value.ObjectValueRepository;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
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
