package pl.softech.eav.domain.specification;

import static com.google.common.base.Preconditions.checkNotNull;
import pl.softech.eav.domain.object.MyObject;
import pl.softech.eav.domain.relation.RelationConfiguration;

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
