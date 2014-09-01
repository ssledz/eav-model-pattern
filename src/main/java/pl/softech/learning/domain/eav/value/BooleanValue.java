package pl.softech.learning.domain.eav.value;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BooleanValue extends AbstractValue<Boolean> {

	@Column(name = "boolean_value")
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
