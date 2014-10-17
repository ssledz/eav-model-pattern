package pl.softech.eav.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author ssledz
 * @since 1.0 
 */
@MappedSuperclass
public class AbstractEntity implements Entity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	protected Long id;

	protected AbstractEntity() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public boolean isNew() {
		return id == null;
	}

	@Override
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
		sb.append("id", id);
		return sb.toString();
	}

}
