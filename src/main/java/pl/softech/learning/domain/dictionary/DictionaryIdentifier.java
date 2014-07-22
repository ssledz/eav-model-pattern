package pl.softech.learning.domain.dictionary;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.Embeddable;

import pl.softech.learning.domain.AbstractValueObject;

@Embeddable
public class DictionaryIdentifier extends AbstractValueObject {

	public static final String IDENTIFIER_PROPERTY = "identifier";
	
	private String identifier;

	protected DictionaryIdentifier() {
	}

	public DictionaryIdentifier(String identifier) {
		this.identifier = checkNotNull(identifier);
	}

	public String getIdentifier() {
		return identifier;
	}

}
