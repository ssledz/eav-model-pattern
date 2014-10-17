package pl.softech.eav.domain.dictionary;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import pl.softech.eav.domain.AbstractValueObject;
import pl.softech.eav.domain.TextMedium;

/**
 * @author ssledz
 * @since 1.0
 */
@Embeddable
public class DictionaryIdentifier extends AbstractValueObject {

	public static final String IDENTIFIER_PROPERTY = "identifier";
	
	@TextMedium
	@Column(nullable = false)
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
