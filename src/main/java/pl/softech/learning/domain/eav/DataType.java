package pl.softech.learning.domain.eav;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import pl.softech.learning.domain.AbstractValueObject;
import pl.softech.learning.domain.dictionary.Dictionary;

@Embeddable
public class DataType extends AbstractValueObject {

	public enum Type {
		TEXT, DOUBLE, INTEGER, DICTIONARY, BOOLEAN, DATE
	}

	@Enumerated(EnumType.STRING)
	private Type type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dictionary_id")
	private Dictionary dictionary;

	protected DataType() {
	}

	public DataType(Type type) {
		this.type = checkNotNull(type);
		checkArgument(type != Type.DICTIONARY);
	}

	public DataType(Dictionary dictionary) {
		this.type = Type.DICTIONARY;
		this.dictionary = checkNotNull(dictionary);
	}

	public Type getType() {
		return type;
	}

	public Dictionary getDictionary() {
		return dictionary;
	}

}
