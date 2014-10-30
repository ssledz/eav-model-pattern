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

import pl.softech.eav.domain.attribute.DataTypeSerialisationService;
import pl.softech.eav.domain.dictionary.DictionaryRepository;
import pl.softech.eav.domain.dsl.CreateModelVisitor;
import pl.softech.eav.domain.dsl.Parser;
import pl.softech.eav.domain.dsl.SymbolTable;
import pl.softech.eav.domain.frame.FrameFactory;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.2
 */
@Service
public class DocumentManagementService {

	private final FrameFactory frameFactory;

	private final DictionaryRepository dictionaryRepository;

	private final DataTypeSerialisationService dataTypeSerialisationService;

	@Autowired
	public DocumentManagementService(FrameFactory frameFactory, DictionaryRepository dictionaryRepository,
			DataTypeSerialisationService dataTypeSerialisationService) {
		this.frameFactory = frameFactory;
		this.dictionaryRepository = dictionaryRepository;
		this.dataTypeSerialisationService = dataTypeSerialisationService;
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

	public SymbolTable loadConfigurationFromFile(String filename) throws Exception {

		CreateModelVisitor visitor = new CreateModelVisitor(dictionaryRepository, dataTypeSerialisationService);
		Parser p = new Parser(visitor);
		p.parse(readConfiguration(filename));

		return visitor.getSymbolTable();

	}

}
