package pl.softech.learning.domain.eav.relation;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import pl.softech.learning.domain.AbstractValueObject;

@Embeddable
@Access(AccessType.FIELD)
public class RelationIdentifier extends AbstractValueObject {
	public static final String IDENTIFIER_PROPERTY = "identifier";

	private String identifier;

	protected RelationIdentifier() {
	}

	public RelationIdentifier(String identifier) {
		this.identifier = checkNotNull(identifier);
	}

	public String getIdentifier() {
		return identifier;
	}
}
