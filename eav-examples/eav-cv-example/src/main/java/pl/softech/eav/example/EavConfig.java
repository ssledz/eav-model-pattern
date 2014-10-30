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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.softech.eav.domain.attribute.Attribute;
import pl.softech.eav.domain.attribute.AttributeIdentifier;
import pl.softech.eav.domain.attribute.AttributeRepository;
import pl.softech.eav.domain.attribute.DataTypeSerialisationService;
import pl.softech.eav.domain.dictionary.Dictionary;
import pl.softech.eav.domain.dictionary.DictionaryEntry;
import pl.softech.eav.domain.dictionary.DictionaryEntryIdentifier;
import pl.softech.eav.domain.dictionary.DictionaryEntryRepository;
import pl.softech.eav.domain.dictionary.DictionaryIdentifier;
import pl.softech.eav.domain.dictionary.DictionaryRepository;
import pl.softech.eav.domain.frame.FrameFactory;
import pl.softech.eav.domain.relation.RelationConfiguration;
import pl.softech.eav.domain.relation.RelationConfigurationRepository;
import pl.softech.eav.domain.relation.RelationIdentifier;

import com.google.common.base.Predicate;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.2
 */
@Configuration
public class EavConfig {

	private final AttributeRepositoryImpl attributeRepository = new AttributeRepositoryImpl();

	private final RelationConfigurationRepositoryImpl relationConfigurationRepository = new RelationConfigurationRepositoryImpl();

	private final DictionaryEntryRepositoryImpl dictionaryEntryRepository = new DictionaryEntryRepositoryImpl();

	private final DictionaryRepositoryImpl dictionaryRepository = new DictionaryRepositoryImpl();

	@Bean
	public DictionaryRepositoryImpl dictionaryRepository() {
		return dictionaryRepository;
	}

	@Bean
	public DictionaryEntryRepositoryImpl dictionaryEntryRepository() {
		return dictionaryEntryRepository;
	}

	@Bean
	public RelationConfigurationRepository relationConfigurationRepository() {
		return relationConfigurationRepository;
	}

	@Bean
	public AttributeRepository attributeRepository() {
		return attributeRepository;
	}

	@Bean
	public FrameFactory frameFactory(AttributeRepository attributeRepository,
			RelationConfigurationRepository relationConfigurationRepository) {
		return new FrameFactory(attributeRepository, relationConfigurationRepository);
	}

	@Bean
	public DataTypeSerialisationService dataTypeSerialisationService(DictionaryEntryRepository dictionaryEntryRepository) {
		return new DataTypeSerialisationService(dictionaryEntryRepository);
	}

	private static class DictionaryEntryRepositoryImpl extends SimpleInMemmoryRepository<DictionaryEntry> implements
			DictionaryEntryRepository {

		@Override
		public DictionaryEntry findByIdentifier(final DictionaryEntryIdentifier identifier) {
			return findOne(new Predicate<DictionaryEntry>() {

				@Override
				public boolean apply(DictionaryEntry input) {
					return input.getIdentifier().getIdentifier().equals(identifier.getIdentifier());
				}
			});
		}

	}

	private static class DictionaryRepositoryImpl extends SimpleInMemmoryRepository<Dictionary> implements DictionaryRepository {

		@Override
		public Dictionary findByIdentifier(final DictionaryIdentifier identifier) {
			return findOne(new Predicate<Dictionary>() {

				@Override
				public boolean apply(Dictionary input) {
					return input.getIdentifier().getIdentifier().equals(identifier.getIdentifier());
				}
			});
		}

	}

	private static class RelationConfigurationRepositoryImpl extends SimpleInMemmoryRepository<RelationConfiguration> implements
			RelationConfigurationRepository {

		@Override
		public RelationConfiguration findByIdentifier(final RelationIdentifier identifier) {
			return findOne(new Predicate<RelationConfiguration>() {

				@Override
				public boolean apply(RelationConfiguration input) {
					return input.getIdentifier().getIdentifier().equals(identifier.getIdentifier());
				}
			});
		}

	}

	private static class AttributeRepositoryImpl extends SimpleInMemmoryRepository<Attribute> implements AttributeRepository {
		@Override
		public Attribute findByIdentifier(final AttributeIdentifier identifier) {
			return findOne(new Predicate<Attribute>() {

				@Override
				public boolean apply(Attribute input) {
					return input.getIdentifier().getIdentifier().equals(identifier.getIdentifier());
				}
			});
		}

	}

}
