package pl.softech.learning.domain.eav;

import java.text.ParseException;

import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.softech.learning.domain.dictionary.DictionaryEntryIdentifier;
import pl.softech.learning.domain.dictionary.DictionaryEntryRepository;
import pl.softech.learning.domain.eav.DataType.Type;

import com.google.common.collect.ImmutableMap;

/**
 * @author ssledz 
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
