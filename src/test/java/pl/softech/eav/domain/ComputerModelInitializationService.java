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
import pl.softech.eav.domain.dictionary.Dictionary;
import pl.softech.eav.domain.dictionary.DictionaryEntry;
import pl.softech.eav.domain.dictionary.DictionaryEntryIdentifier;
import pl.softech.eav.domain.dictionary.DictionaryEntryRepository;
import pl.softech.eav.domain.dictionary.DictionaryIdentifier;
import pl.softech.eav.domain.dictionary.DictionaryRepository;
import pl.softech.eav.domain.object.MyObjectRepository;
import pl.softech.eav.domain.value.ObjectValueRepository;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
@Service
public class ComputerModelInitializationService {

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

	private CategoryIdentifier computerCategory;

	public DictionaryIdentifier getComputerType() {
		return computerType;
	}

	public DictionaryIdentifier getOs() {
		return os;
	}

	public DictionaryIdentifier getComputerMake() {
		return computerMake;
	}

	public DictionaryEntryIdentifier getNotebook() {
		return notebook;
	}

	public DictionaryEntryIdentifier getDesktop() {
		return desktop;
	}

	public DictionaryEntryIdentifier getWin7() {
		return win7;
	}

	public DictionaryEntryIdentifier getWin8() {
		return win8;
	}

	public DictionaryEntryIdentifier getLinux() {
		return linux;
	}

	public DictionaryEntryIdentifier getSolaris() {
		return solaris;
	}

	public DictionaryEntryIdentifier getDell() {
		return dell;
	}

	public DictionaryEntryIdentifier getLenovo() {
		return lenovo;
	}

	public DictionaryEntryIdentifier getApple() {
		return apple;
	}

	public CategoryIdentifier getComputerCategory() {
		return computerCategory;
	}

	@Transactional
	public void initializeDict() {

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

	@Transactional
	public void initializeCat() {
		computerCategory = new CategoryIdentifier("computer");
		Category computer = new Category(computerCategory, "Computer");
		categoryRepository.save(computer);
	}

	@Transactional
	public void initializeAtt() {
		Category computer = categoryRepository.findByIdentifier(computerCategory);
		Dictionary computerMakeDict = dictionaryRepository.findByIdentifier(computerMake);
		Dictionary computerTypeDict = dictionaryRepository.findByIdentifier(computerType);
		Dictionary osDict = dictionaryRepository.findByIdentifier(os);
		attributeRepository.save(new Attribute(new AttributeIdentifier("make"), "Make", computer, new DataType(computerMakeDict)));
		attributeRepository.save(new Attribute(new AttributeIdentifier("model"), "Model", computer, new DataType(Type.TEXT)));
		attributeRepository.save(new Attribute(new AttributeIdentifier("type"), "Type", computer, new DataType(computerTypeDict)));
		attributeRepository.save(new Attribute(new AttributeIdentifier("cpu"), "CPU", computer, new DataType(Type.TEXT)));
		attributeRepository.save(new Attribute(new AttributeIdentifier("drive"), "Drive", computer, new DataType(Type.TEXT)));
		attributeRepository.save(new Attribute(new AttributeIdentifier("video"), "Video", computer, new DataType(Type.TEXT)));
		attributeRepository.save(new Attribute(new AttributeIdentifier("ram"), "RAM (GB)", computer, new DataType(Type.INTEGER)));
		attributeRepository.save(new Attribute(new AttributeIdentifier("optical"), "Optical", computer, new DataType(Type.TEXT)));
		attributeRepository.save(new Attribute(new AttributeIdentifier("battery"), "Battery", computer, new DataType(Type.TEXT)));
		attributeRepository.save(new Attribute(new AttributeIdentifier("screen"), "Screen", computer, new DataType(Type.TEXT)));
		attributeRepository.save(new Attribute(new AttributeIdentifier("os"), "OS", computer, new DataType(osDict)));
		attributeRepository.save(new Attribute(new AttributeIdentifier("purshase_date"), "Purschase Date", computer,
				new DataType(Type.DATE)));
		attributeRepository.save(new Attribute(new AttributeIdentifier("for_sale"), "Is for sale ?", computer, new DataType(Type.BOOLEAN)));

	}

	@Transactional
	public void initialize() {
		initializeDict();
		initializeCat();
		initializeAtt();
	}

}
