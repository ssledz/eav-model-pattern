package pl.softech.eav.domain.attribute;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import pl.softech.eav.domain.AbstractValueObject;

@Embeddable
@Access(AccessType.FIELD)
public class AttributeIdentifier extends AbstractValueObject {

	public static final String IDENTIFIER_PROPERTY = "identifier";

	private String identifier;

	protected AttributeIdentifier() {
	}

	public AttributeIdentifier(String identifier) {
		this.identifier = checkNotNull(identifier);
	}

	public String getIdentifier() {
		return identifier;
	}

}
