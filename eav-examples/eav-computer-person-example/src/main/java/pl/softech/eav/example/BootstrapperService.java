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
@Transactional(readOnly = true)
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
		DictionaryIdentifier computerType = new DictionaryIdentifier("computer_type");
		DictionaryIdentifier os = new DictionaryIdentifier("os");
		DictionaryIdentifier computerMake = new DictionaryIdentifier("computer_make");

		dictionaryRepository.save(Arrays.asList(//
				new Dictionary(computerType, "Computer Type"),//
				new Dictionary(os, "Operating System"),//
				new Dictionary(computerMake, "Computer Make")//
				));

		DictionaryEntryIdentifier notebook = new DictionaryEntryIdentifier("notebook");
		DictionaryEntryIdentifier desktop = new DictionaryEntryIdentifier("desktop");
		DictionaryEntryIdentifier win7 = new DictionaryEntryIdentifier("win7");
		DictionaryEntryIdentifier win8 = new DictionaryEntryIdentifier("win8");
		DictionaryEntryIdentifier linux = new DictionaryEntryIdentifier("linux");
		DictionaryEntryIdentifier solaris = new DictionaryEntryIdentifier("solaris");
		DictionaryEntryIdentifier dell = new DictionaryEntryIdentifier("dell");
		DictionaryEntryIdentifier lenovo = new DictionaryEntryIdentifier("lenovo");
		DictionaryEntryIdentifier apple = new DictionaryEntryIdentifier("apple");

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
		
		LOGGER.info("Done.");

	}

}
