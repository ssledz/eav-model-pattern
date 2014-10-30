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
import pl.softech.eav.domain.relation.Relation;
import pl.softech.eav.domain.relation.RelationConfiguration;
import pl.softech.eav.domain.relation.RelationConfigurationRepository;
import pl.softech.eav.domain.relation.RelationIdentifier;
import pl.softech.eav.domain.value.DictionaryEntryValue;
import pl.softech.eav.domain.value.ObjectValueRepository;
import pl.softech.eav.domain.value.StringValue;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
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
		
		Relation relation = person.getRelationByIdentifier(new RelationIdentifier("has_computer"));
		MyObject computer3 = relation.getTarget();
		
		Assert.assertTrue(computer == computer3);
		
		try {
			person.addRelation(hasComputer, person2);
			Assert.fail();
		}catch(Exception e) {
			
		}

	}
}
