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

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.softech.eav.domain.dictionary.Dictionary;
import pl.softech.eav.domain.dictionary.DictionaryEntry;
import pl.softech.eav.domain.dictionary.DictionaryEntryIdentifier;
import pl.softech.eav.domain.dictionary.DictionaryEntryRepository;
import pl.softech.eav.domain.dictionary.DictionaryIdentifier;
import pl.softech.eav.domain.dictionary.DictionaryRepository;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.2
 */
@Service
public class BootstrapperService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BootstrapperService.class);

	private final DictionaryRepository dictionaryRepository;
	private final DictionaryEntryRepository dictionaryEntryRepository;

	@Autowired
	public BootstrapperService(DictionaryRepository dictionaryRepository, DictionaryEntryRepository dictionaryEntryRepository) {
		super();
		this.dictionaryRepository = dictionaryRepository;
		this.dictionaryEntryRepository = dictionaryEntryRepository;
	}

	@Transactional
	public void onApplicationStart() {

		if (dictionaryRepository.count() > 0) {
			LOGGER.info("Dictionaries are not empty so there is no need to populate them with data.");
			return;
		}

		LOGGER.info("Populating dictionaries with data");
		DictionaryIdentifier os = new DictionaryIdentifier("knowledge-topic-os");
		DictionaryIdentifier ws = new DictionaryIdentifier("knowledge-topic-ws");
		DictionaryIdentifier ts = new DictionaryIdentifier("knowledge-topic-ts");
		DictionaryIdentifier ci = new DictionaryIdentifier("knowledge-topic-ci");
		DictionaryIdentifier sof = new DictionaryIdentifier("knowledge-topic-sof");
		DictionaryIdentifier pl = new DictionaryIdentifier("knowledge-topic-pl");
		DictionaryIdentifier java = new DictionaryIdentifier("knowledge-topic-java");
		DictionaryIdentifier db = new DictionaryIdentifier("knowledge-topic-db");
		DictionaryIdentifier w3c = new DictionaryIdentifier("knowledge-topic-w3c");

		dictionaryRepository.save(Arrays.asList(//
				new Dictionary(os, "Operating System knowledge"),//
				new Dictionary(ws, "Web Server knowledge"),//
				new Dictionary(ts, "Tracking System knowledge"),//
				new Dictionary(ci, "Continuous Integration Server knowledge"),//
				new Dictionary(sof, "Software knowledge"),//
				new Dictionary(pl, "Programming Language knowledge"),//
				new Dictionary(java, " Java knowledge"),//
				new Dictionary(db, "Database knowledge"),//
				new Dictionary(w3c, "W3C Standard knowledge")//
				));

		DictionaryEntryIdentifier win7 = new DictionaryEntryIdentifier("win7");
		DictionaryEntryIdentifier win8 = new DictionaryEntryIdentifier("win8");
		DictionaryEntryIdentifier linux = new DictionaryEntryIdentifier("linux");
		DictionaryEntryIdentifier solaris = new DictionaryEntryIdentifier("solaris");

		dictionaryEntryRepository.save(Arrays.asList(//
				new DictionaryEntry(dictionaryRepository.findByIdentifier(os), win7, "Windows 7"),//
				new DictionaryEntry(dictionaryRepository.findByIdentifier(os), win8, "Window 8"),//
				new DictionaryEntry(dictionaryRepository.findByIdentifier(os), linux, "Linux"),//
				new DictionaryEntry(dictionaryRepository.findByIdentifier(os), solaris, "Solaris")//
				));

		LOGGER.info("Done.");

	}

}
