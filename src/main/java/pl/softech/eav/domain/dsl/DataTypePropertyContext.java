package pl.softech.eav.domain.dsl;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author ssledz
 * @since 1.0 
 */
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
