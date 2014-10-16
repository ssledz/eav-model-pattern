package pl.softech.eav.domain.frame;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.Method;
import java.util.Collection;

import pl.softech.eav.domain.attribute.AttributeIdentifier;
import pl.softech.eav.domain.attribute.AttributeRepository;
import pl.softech.eav.domain.object.MyObject;
import pl.softech.eav.domain.value.AbstractValue;
import pl.softech.eav.domain.value.ObjectValue;
import pl.softech.eav.domain.value.ValueFactory;
import pl.softech.eav.domain.value.ValueVisitorAdapter;

import com.google.common.collect.ImmutableSet;

/**
 * @author ssledz
 */
public class AttributeProperty implements Property {

	private final MyObject object;

	private final AttributeRepository attributeRepository;

	private final ValueFactory valueFactory;

	private final String attributeIdentifier;

	AttributeProperty(MyObject object, String attributeIdentifier, AttributeRepository attributeRepository, ValueFactory valueFactory) {
		this.object = object;
		this.attributeIdentifier = attributeIdentifier;
		this.attributeRepository = attributeRepository;
		this.valueFactory = valueFactory;
	}

	@Override
	public void setValue(Method method, Object arg) {
		setValue(attributeIdentifier, method, arg);
	}

	@Override
	public void addValue(Method method, Object arg) {
		addValue(attributeIdentifier, method, arg);
	}

	@Override
	public Object getValues(Method method) {
		try {
			return getValues(attributeIdentifier, method);
		} catch (Exception e) {
			// TODO make concrete exception type
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object getValue(Method method) {
		return getValue(attributeIdentifier, method);
	}

	private void setValue(String attIdentifier, Method method, Object arg) {

		pl.softech.eav.domain.attribute.Attribute attribute = attributeRepository.findByIdentifier(new AttributeIdentifier(attIdentifier));

		if (arg == null) {
			object.updateValue(attribute, null);
			return;
		}

		AbstractValue<?> value = valueFactory.create(arg);

		object.updateValue(attribute, value);
	}

	private void addValue(String attIdentifier, Method method, Object arg) {

		checkNotNull(arg);
		
		pl.softech.eav.domain.attribute.Attribute attribute = attributeRepository.findByIdentifier(new AttributeIdentifier(attIdentifier));
		
		AbstractValue<?> value = valueFactory.create(arg);
		object.addValue(attribute, value);

	}

	@SuppressWarnings("unchecked")
	private Object getValues(String attIdentifier, Method method) throws Exception {

		ImmutableSet<ObjectValue> values = object.getValuesByAttribute(new AttributeIdentifier(attIdentifier));

		if (values == null) {
			return null;
		}

		Class<?> collType = method.getReturnType();
		
		final Collection<Object>[] bag = new Collection[1];

		bag[0] = ReflectionUtils.newCollection(collType);
		
		for (ObjectValue value : values) {
			value.accept(new ValueVisitorAdapter() {
				@Override
				protected void visitAny(AbstractValue<?> value) {
					bag[0].add(value.getValue());
				}
			});
		}

		return bag[0];
	}

	private Object getValue(String attIdentifier, Method method) {

		ObjectValue value = object.getValueByAttribute(new AttributeIdentifier(attIdentifier));

		if (value == null) {
			return null;
		}

		final Object[] bag = new Object[1];

		value.accept(new ValueVisitorAdapter() {
			@Override
			protected void visitAny(AbstractValue<?> value) {
				bag[0] = value.getValue();
			}
		});

		checkNotNull(bag[0]);

		return bag[0];
	}

}
