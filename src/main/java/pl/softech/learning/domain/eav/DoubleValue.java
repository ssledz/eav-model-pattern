package pl.softech.learning.domain.eav;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DoubleValue extends AbstractValue<Double>{

	@Column(name = "double_value")
	private Double value;

	protected DoubleValue() {
	}
	
	public DoubleValue(Double value) {
		this.value = checkNotNull(value);
	}

	@Override
	public Double getValue() {
		return value;
	}

	@Override
	public void accept(ValueVisitor visitor) {
		visitor.visit(this);
	}


	
	
	
}
