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
package pl.softech.eav.domain.attribute;

import java.text.ParseException;

import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.softech.eav.domain.attribute.DataType.Type;
import pl.softech.eav.domain.dictionary.DictionaryEntryIdentifier;
import pl.softech.eav.domain.dictionary.DictionaryEntryRepository;
import pl.softech.eav.domain.value.AbstractValue;
import pl.softech.eav.domain.value.BooleanValue;
import pl.softech.eav.domain.value.DateValue;
import pl.softech.eav.domain.value.DictionaryEntryValue;
import pl.softech.eav.domain.value.DoubleValue;
import pl.softech.eav.domain.value.IntegerValue;
import pl.softech.eav.domain.value.StringValue;
import pl.softech.eav.domain.value.ValueReader;

import com.google.common.collect.ImmutableMap;

/**
 * @author ssledz
 * @since 1.0 
 */
@Service
public class DataTypeSerialisationService {

	private static final FastDateFormat DF = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

	private ImmutableMap<Type, ValueReader> type2valueReader = new ImmutableMap.Builder<Type, ValueReader>()//

			.put(Type.TEXT, new ValueReader() {

				@Override
				public AbstractValue<?> readValue(String value) {
					return new StringValue(value);
				}

			})//
			.put(Type.DOUBLE, new ValueReader() {

				@Override
				public AbstractValue<?> readValue(String value) {
					return new DoubleValue(Double.parseDouble(value));
				}

			})//
			.put(Type.INTEGER, new ValueReader() {

				@Override
				public AbstractValue<?> readValue(String value) {
					return new IntegerValue(Integer.parseInt(value));
				}

			})//
			.put(Type.DICTIONARY, new ValueReader() {

				@Override
				public AbstractValue<?> readValue(String value) {
					return new DictionaryEntryValue(dictionaryEntryRepository.findByIdentifier(new DictionaryEntryIdentifier(value)));
				}

			})//
			.put(Type.BOOLEAN, new ValueReader() {

				@Override
				public AbstractValue<?> readValue(String value) {
					return new BooleanValue(Boolean.parseBoolean(value));
				}

			})//
			.put(Type.DATE, new ValueReader() {

				@Override
				public AbstractValue<?> readValue(String value) {
					try {
						return new DateValue(DF.parse(value));
					} catch (ParseException e) {
						throw new RuntimeException(e);
					}
				}

			})//
			.build();

	private final DictionaryEntryRepository dictionaryEntryRepository;

	@Autowired
	public DataTypeSerialisationService(DictionaryEntryRepository dictionaryEntryRepository) {
		this.dictionaryEntryRepository = dictionaryEntryRepository;
	}

	public AbstractValue<?> readValue(Type type, String value) {
		return type2valueReader.get(type).readValue(value);
	}

}
