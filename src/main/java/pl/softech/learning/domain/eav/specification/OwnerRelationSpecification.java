package pl.softech.learning.domain.eav.specification;

import static com.google.common.base.Preconditions.checkNotNull;
import pl.softech.learning.domain.eav.MyObject;
import pl.softech.learning.domain.eav.relation.RelationConfiguration;
import pl.softech.learning.domain.specification.Specification;

/**
 * @author ssledz
 */
public class OwnerRelationSpecification implements Specification<MyObject> {

	private final RelationConfiguration configurarion;

	public OwnerRelationSpecification(RelationConfiguration configurarion) {
		this.configurarion = checkNotNull(configurarion);
	}

	@Override
	public boolean isSafisfiedBy(MyObject owner) {
		return configurarion.getOwner().getIdentifier().equals(owner.getCategory().getIdentifier());
	}

}
