package pl.softech.learning.domain.eav.relation;

import static com.google.common.base.Preconditions.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import pl.softech.learning.domain.AbstractEntity;
import pl.softech.learning.domain.eav.MyObject;
import pl.softech.learning.domain.eav.specification.OwnerRelationSpecification;
import pl.softech.learning.domain.eav.specification.TargetRelationSpecification;

/**
 * @author ssledz 
 */
@Entity
public class Relation extends AbstractEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	private RelationConfiguration configurarion;

	@ManyToOne(fetch = FetchType.LAZY)
	private MyObject owner;

	@ManyToOne(fetch = FetchType.LAZY)
	private MyObject target;

	protected Relation() {
	}

	public Relation(RelationConfiguration configurarion, MyObject owner, MyObject target) {

		this.configurarion = checkNotNull(configurarion);
		this.owner = checkNotNull(owner);
		this.target = checkNotNull(target);
		
		checkArgument(new TargetRelationSpecification(configurarion).isSafisfiedBy(target));
		checkArgument(new OwnerRelationSpecification(configurarion).isSafisfiedBy(owner));

	}
	
	@Override
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
		sb.appendSuper(super.toString());
		sb.append(configurarion);
		sb.append("owner", owner);
		sb.append("target", target);
		return sb.toString();
	}

}
