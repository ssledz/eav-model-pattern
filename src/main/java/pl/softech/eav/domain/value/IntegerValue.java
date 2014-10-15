package pl.softech.eav.domain.value;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IntegerValue extends AbstractValue<Integer> {

	@Column(name = "integer_value")
	private Integer value;

	protected IntegerValue() {
	}

	public IntegerValue(Integer value) {
		this.value = checkNotNull(value);
	}

	@Override
	public Integer getValue() {
		return value;
	}

	@Override
	public void accept(ValueVisitor visitor) {
		visitor.visit(this);
	}

}
