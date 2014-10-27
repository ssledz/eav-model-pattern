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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import pl.softech.eav.domain.attribute.AttributeRepository;
import pl.softech.eav.domain.attribute.DataTypeSerialisationService;
import pl.softech.eav.domain.dictionary.DictionaryEntryRepository;
import pl.softech.eav.domain.frame.FrameFactory;
import pl.softech.eav.domain.relation.RelationConfigurationRepository;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.2
 */
@Configuration
@EnableJpaRepositories("pl.softech.eav.domain")
public class EavConfig {

	@Bean
	public FrameFactory frameFactory(AttributeRepository attributeRepository,
			RelationConfigurationRepository relationConfigurationRepository) {
		return new FrameFactory(attributeRepository, relationConfigurationRepository);
	}

	@Bean
	public DataTypeSerialisationService dataTypeSerialisationService(DictionaryEntryRepository dictionaryEntryRepository) {
		return new DataTypeSerialisationService(dictionaryEntryRepository);
	}


}
