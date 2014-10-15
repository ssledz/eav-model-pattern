package pl.softech.eav.domain.value;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import pl.softech.eav.domain.AbstractEntity;
import pl.softech.eav.domain.attribute.Attribute;
import pl.softech.eav.domain.object.MyObject;

@Entity
public class ObjectValue extends AbstractEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	private Attribute attribute;

	@ManyToOne(fetch = FetchType.LAZY)
	private MyObject object;

	@Embedded
	private StringValue stringValue;
	@Embedded
	private IntegerValue integerValue;
	@Embedded
	private DoubleValue doubleValue;
	@Embedded
	private DateValue dateValue;
	@Embedded
	private BooleanValue booleanValue;
	@Embedded
	private DictionaryEntryValue dictionaryEntryValue;

	protected ObjectValue() {
	}

	private ObjectValue(Attribute attribute, MyObject object) {
		this.attribute = checkNotNull(attribute);
		this.object = checkNotNull(object);
	}

	public ObjectValue(Attribute attribute, MyObject object, StringValue stringValue) {
		this(attribute, object);
		this.stringValue = checkNotNull(stringValue);
	}

	public ObjectValue(Attribute attribute, MyObject object, IntegerValue integerValue) {
		this(attribute, object);
		this.integerValue = checkNotNull(integerValue);
	}

	public ObjectValue(Attribute attribute, MyObject object, DoubleValue doubleValue) {
		this(attribute, object);
		this.doubleValue = checkNotNull(doubleValue);
	}

	public ObjectValue(Attribute attribute, MyObject object, DateValue dateValue) {
		this(attribute, object);
		this.dateValue = checkNotNull(dateValue);
	}

	public ObjectValue(Attribute attribute, MyObject object, BooleanValue booleanValue) {
		this(attribute, object);
		this.booleanValue = checkNotNull(booleanValue);
	}

	public ObjectValue(Attribute attribute, MyObject object, DictionaryEntryValue dictionaryEntryValue) {
		this(attribute, object);
		this.dictionaryEntryValue = checkNotNull(dictionaryEntryValue);
	}

	private AbstractValue<?>[] getAllFields() {
		return new AbstractValue<?>[] { stringValue, integerValue, doubleValue, dateValue, booleanValue, dictionaryEntryValue };
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void accept(ValueVisitor visitor) {

		for (AbstractValue<?> value : getAllFields()) {

			if (value != null) {
				value.accept(visitor);
			}

		}

	}

	public String getValueAsString() {

		final String[] bag = new String[1];

		ValueVisitor visitor = new ValueVisitorAdapter() {
			@Override
			protected void visitAny(AbstractValue<?> value) {
				bag[0] = value.asString();
			}
		};

		accept(visitor);

		return bag[0];

	}

	@Override
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
		sb.appendSuper(super.toString());
		sb.append("attribute", attribute);
		sb.append("value", getValueAsString());
		return sb.toString();
	}

}
