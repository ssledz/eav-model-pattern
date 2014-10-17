package pl.softech.eav.domain.value;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.Embeddable;

/**
 * @author ssledz
 * @since 1.0
 */
@Embeddable
public class BooleanValue extends AbstractValue<Boolean> {

	private Boolean value;

	protected BooleanValue() {
	}

	public BooleanValue(Boolean value) {
		this.value = checkNotNull(value);
	}

	@Override
	public Boolean getValue() {
		return value;
	}

	@Override
	public void accept(ValueVisitor visitor) {
		visitor.visit(this);
	}

}
