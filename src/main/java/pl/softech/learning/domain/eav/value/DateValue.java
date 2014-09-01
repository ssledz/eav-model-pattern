package pl.softech.learning.domain.eav.value;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class DateValue extends AbstractValue<Date> {

	@Column(name = "date_value")
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
