package pl.softech.eav.domain.value;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.Embeddable;

/**
 * @author ssledz
 */
@Embeddable
public class StringValue extends AbstractValue<String> {

	private String value;

	protected StringValue() {
	}

	public StringValue(String value) {
		this.value = checkNotNull(value);
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void accept(ValueVisitor visitor) {
		visitor.visit(this);
	}

}
