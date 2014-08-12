package pl.softech.learning.domain.eav;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class StringValue extends AbstractValue<String> {

	@Column(name = "string_value")
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
