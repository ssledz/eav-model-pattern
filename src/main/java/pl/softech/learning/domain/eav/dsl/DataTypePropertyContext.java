package pl.softech.learning.domain.eav.dsl;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DataTypePropertyContext implements Context {

	private final String dataType;

	public DataTypePropertyContext(String dataType) {
		this.dataType = dataType;
	}

	public String getDataType() {
		return dataType;
	}

	@Override
	public void accept(ContextVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
		sb.append("dataType", dataType);
		return sb.toString();
	}
}
