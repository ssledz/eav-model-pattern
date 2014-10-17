package pl.softech.eav.domain.relation;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import pl.softech.eav.domain.AbstractEntity;
import pl.softech.eav.domain.object.MyObject;
import pl.softech.eav.domain.specification.OwnerRelationSpecification;
import pl.softech.eav.domain.specification.TargetRelationSpecification;

/**
 * @author ssledz
 * @since 1.0
 */
@Entity
@Table(name = "relation")
public class Relation extends AbstractEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rel_cnf_id", nullable = false)
	private RelationConfiguration configuration;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id", nullable = false)
	private MyObject owner;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "target_id")
	//nullable is true because target can be persisted before relation
	private MyObject target;

	protected Relation() {
	}

	public Relation(RelationConfiguration configurarion, MyObject owner, MyObject target) {

		this.configuration = checkNotNull(configurarion, ARG_NOT_NULL_CHECK, "configuration");
		this.owner = checkNotNull(owner, ARG_NOT_NULL_CHECK, "owner");
		this.target = checkNotNull(target, ARG_NOT_NULL_CHECK, "target");

		checkArgument(new TargetRelationSpecification(configurarion).isSafisfiedBy(target));
		checkArgument(new OwnerRelationSpecification(configurarion).isSafisfiedBy(owner));

	}

	public RelationConfiguration getConfiguration() {
		return configuration;
	}

	public MyObject getTarget() {
		return target;
	}

	@Override
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
		sb.appendSuper(super.toString());
		sb.append(configuration);
		sb.append("owner", owner);
		sb.append("target", target);
		return sb.toString();
	}

}
