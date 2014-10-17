package pl.softech.eav.domain.specification;

import static com.google.common.base.Preconditions.checkNotNull;
import pl.softech.eav.domain.object.MyObject;
import pl.softech.eav.domain.relation.RelationConfiguration;

/**
 * @author ssledz
 * @since 1.0
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
