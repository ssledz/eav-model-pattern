package pl.softech.learning.domain.eav.dsl;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author ssledz 
 */
public class TargetPropertyContext implements Context {

	private final String name;

	public TargetPropertyContext(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public void accept(ContextVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
		sb.append("name", name);
		return sb.toString();
	}

}
