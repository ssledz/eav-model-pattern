package pl.softech.eav.domain.value;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.AssociationOverride;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import pl.softech.eav.domain.AbstractEntity;
import pl.softech.eav.domain.attribute.Attribute;
import pl.softech.eav.domain.object.MyObject;

/**
 * @author ssledz
 */
@Entity
@Table(name = "object_value")
public class ObjectValue extends AbstractEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "attribute_id", nullable = false)
	private Attribute attribute;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "object_id", nullable = false)
	private MyObject object;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "string_value"))
	private StringValue stringValue;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "integer_value"))
	private IntegerValue integerValue;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "double_value"))
	private DoubleValue doubleValue;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "date_value"))
	private DateValue dateValue;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "boolean_value"))
	private BooleanValue booleanValue;

	@Embedded
	@AssociationOverride(name="value", joinColumns=@JoinColumn(name="dictionary_entry_id"))
	private DictionaryEntryValue dictionaryEntryValue;

	protected ObjectValue() {
	}

	private ObjectValue(Attribute attribute, MyObject object) {
		this.attribute = checkNotNull(attribute, ARG_NOT_NULL_CHECK, "attribute");
		this.object = checkNotNull(object, ARG_NOT_NULL_CHECK, "object");
	}

	public ObjectValue(Attribute attribute, MyObject object, StringValue stringValue) {
		this(attribute, object);
		this.stringValue = checkNotNull(stringValue, ARG_NOT_NULL_CHECK, "stringValue");
	}

	public ObjectValue(Attribute attribute, MyObject object, IntegerValue integerValue) {
		this(attribute, object);
		this.integerValue = checkNotNull(integerValue, ARG_NOT_NULL_CHECK, "integerValue");
	}

	public ObjectValue(Attribute attribute, MyObject object, DoubleValue doubleValue) {
		this(attribute, object);
		this.doubleValue = checkNotNull(doubleValue, ARG_NOT_NULL_CHECK, "doubleValue");
	}

	public ObjectValue(Attribute attribute, MyObject object, DateValue dateValue) {
		this(attribute, object);
		this.dateValue = checkNotNull(dateValue, ARG_NOT_NULL_CHECK, "dateValue");
	}

	public ObjectValue(Attribute attribute, MyObject object, BooleanValue booleanValue) {
		this(attribute, object);
		this.booleanValue = checkNotNull(booleanValue, ARG_NOT_NULL_CHECK, "booleanValue");
	}

	public ObjectValue(Attribute attribute, MyObject object, DictionaryEntryValue dictionaryEntryValue) {
		this(attribute, object);
		this.dictionaryEntryValue = checkNotNull(dictionaryEntryValue, ARG_NOT_NULL_CHECK, "dictionaryEntryValue");
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
