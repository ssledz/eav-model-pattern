package pl.softech.learning.domain.eav.specification;

import static com.google.common.base.Preconditions.checkNotNull;
import pl.softech.learning.domain.eav.MyObject;
import pl.softech.learning.domain.eav.relation.RelationConfiguration;
import pl.softech.learning.domain.specification.Specification;

/**
 * @author ssledz 
 */
public class TargetRelationSpecification implements Specification<MyObject> {

	private final RelationConfiguration configurarion;

	public TargetRelationSpecification(RelationConfiguration configurarion) {
		this.configurarion = checkNotNull(configurarion);
	}

	@Override
	public boolean isSafisfiedBy(MyObject target) {
		return configurarion.getTarget().getIdentifier().equals(target.getCategory().getIdentifier());
	}

}
