package pl.softech.eav.domain.value;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author ssledz
 */
@Embeddable
public class DateValue extends AbstractValue<Date> {

	@Temporal(TemporalType.DATE)
	private Date date;

	protected DateValue() {
	}

	public DateValue(Date date) {
		this.date = checkNotNull(date);
	}

	@Override
	public Date getValue() {
		return date;
	}

	@Override
	public void accept(ValueVisitor visitor) {
		visitor.visit(this);
	}

}
