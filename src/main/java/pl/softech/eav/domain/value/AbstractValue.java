package pl.softech.eav.domain.value;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author ssledz
 * @since 1.0
 */
public abstract class AbstractValue<T> {

	public abstract T getValue();

	public String asString() {
		return isSet() ? getValue().toString() : "null";
	}

	public boolean isSet() {
		return getValue() != null;
	};

	public abstract void accept(ValueVisitor visitor);

	@Override
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
		sb.append("value", getValue());
		return sb.toString();
	}

	@Override
	public int hashCode() {
		return 31 * 2 + (isSet() ? 0 : getValue().hashCode());
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		return new EqualsBuilder().append(getValue(), ((AbstractValue<?>) obj).getValue()).isEquals();

	}

}
