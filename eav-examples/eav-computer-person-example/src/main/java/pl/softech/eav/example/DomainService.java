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
package pl.softech.eav.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.softech.eav.domain.attribute.AttributeRepository;
import pl.softech.eav.domain.attribute.DataTypeSerialisationService;
import pl.softech.eav.domain.category.CategoryRepository;
import pl.softech.eav.domain.dictionary.DictionaryRepository;
import pl.softech.eav.domain.dsl.CreateModelVisitor;
import pl.softech.eav.domain.dsl.Parser;
import pl.softech.eav.domain.frame.FrameFactory;
import pl.softech.eav.domain.object.MyObject;
import pl.softech.eav.domain.object.MyObjectRepository;
import pl.softech.eav.domain.relation.RelationConfigurationRepository;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.2
 */
@Service
@Transactional(readOnly = true)
public class DomainService {

	private final DataTypeSerialisationService dataTypeSerialisationService;

	private final CategoryRepository categoryRepository;

	private final AttributeRepository attributeRepository;

	private final MyObjectRepository myObjectRepository;

	private final RelationConfigurationRepository relationConfigurationRepository;

	private final FrameFactory frameFactory;

	private final DictionaryRepository dictionaryRepository;

	@Autowired
	public DomainService(DataTypeSerialisationService dataTypeSerialisationService, CategoryRepository categoryRepository,
			AttributeRepository attributeRepository, MyObjectRepository myObjectRepository,
			RelationConfigurationRepository relationConfigurationRepository, FrameFactory frameFactory,
			DictionaryRepository dictionaryRepository) {
		super();
		this.dataTypeSerialisationService = dataTypeSerialisationService;
		this.categoryRepository = categoryRepository;
		this.attributeRepository = attributeRepository;
		this.myObjectRepository = myObjectRepository;
		this.relationConfigurationRepository = relationConfigurationRepository;
		this.frameFactory = frameFactory;
		this.dictionaryRepository = dictionaryRepository;
	}

	private String readConfiguration(String filename) throws Exception {

		ClassPathResource rs = new ClassPathResource(filename);

		StringBuffer buffer = new StringBuffer();
		try (InputStreamReader in = new InputStreamReader(rs.getInputStream())) {
			try (BufferedReader bin = new BufferedReader(in)) {
				String line;
				while ((line = bin.readLine()) != null) {
					buffer.append(line).append("\n");
				}
			}
		}

		return buffer.toString();
	}

	@Transactional
	public void loadConfigurationFromFile(String filename) throws Exception {

		CreateModelVisitor visitor = new CreateModelVisitor(dictionaryRepository, dataTypeSerialisationService);
		Parser p = new Parser(visitor);
		p.parse(readConfiguration(filename));

		categoryRepository.save(visitor.getCategories());
		attributeRepository.save(visitor.getAttributes());
		relationConfigurationRepository.save(visitor.getRelations());
		myObjectRepository.save(visitor.getObjects());

	}

	public Person findPersonByName(String name) {
		MyObject person = myObjectRepository.findByName(name);
		if (person == null) {
			return null;
		}
		return frameFactory.frame(Person.class, person);
	}

	public Computer findComputerByName(String name) {
		MyObject computer = myObjectRepository.findByName(name);
		if (computer == null) {
			return null;
		}
		return frameFactory.frame(Computer.class, computer);
	}

}
