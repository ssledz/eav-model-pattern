package pl.softech.eav.domain.value;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.Embeddable;

/**
 * @author ssledz
 */
@Embeddable
public class DoubleValue extends AbstractValue<Double>{

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
