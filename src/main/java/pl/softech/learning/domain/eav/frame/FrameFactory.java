package pl.softech.learning.domain.eav.frame;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static pl.softech.learning.domain.eav.frame.ReflectionUtils.isAdd;
import static pl.softech.learning.domain.eav.frame.ReflectionUtils.isCollection;
import static pl.softech.learning.domain.eav.frame.ReflectionUtils.isGetter;
import static pl.softech.learning.domain.eav.frame.ReflectionUtils.isSetter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import pl.softech.learning.domain.eav.AttributeIdentifier;
import pl.softech.learning.domain.eav.AttributeRepository;
import pl.softech.learning.domain.eav.MyObject;
import pl.softech.learning.domain.eav.value.AbstractValue;
import pl.softech.learning.domain.eav.value.ObjectValue;
import pl.softech.learning.domain.eav.value.ValueFactory;
import pl.softech.learning.domain.eav.value.ValueVisitorAdapter;

import com.google.common.collect.ImmutableSet;

/**
 * @author ssledz
 */
public class FrameFactory {

	private AttributeRepository attributeRepository;

	private ValueFactory valueFactory = new ValueFactory();

	public FrameFactory(AttributeRepository attributeRepository) {
		this.attributeRepository = attributeRepository;
	}

	private class MyInvocationHandler implements InvocationHandler {

		private final MyObject object;

		public MyInvocationHandler(MyObject object) {
			this.object = checkNotNull(object);
		}

		private String getAttributeIdentifier(Method method) {
			
			Attribute ann = method.getAnnotation(Attribute.class);
			if (ann != null && ann.name().length() > 0) {
				return ann.name();
			}

			String name = method.getName();

			for (String prefix : Arrays.asList("get", "is", "set", "add")) {
				if (name.startsWith(prefix)) {
					name = name.substring(prefix.length());
					break;
				}
			}

			return name.toLowerCase();
		}

		@SuppressWarnings("unchecked")
		private Object getValues(String attIdentifier, Method method) throws Exception {

			ImmutableSet<ObjectValue> values = object.getValuesByAttribute(new AttributeIdentifier(attIdentifier));

			if (values == null) {
				return null;
			}

			Class<?> collType = method.getReturnType();
			final Collection<Object>[] bag = new Collection[1];
			if (collType.isInterface()) {

				if (collType.isAssignableFrom(ArrayList.class)) {

					bag[0] = new ArrayList<>();

				} else if (collType.isAssignableFrom(HashSet.class)) {

					bag[0] = new HashSet<>();

				}

			} else {

				bag[0] = (Collection<Object>) collType.getConstructor(new Class<?>[] {}).newInstance();

			}

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

		private void setValue(String attIdentifier, Method method, Object arg) {

			pl.softech.learning.domain.eav.Attribute attribute = attributeRepository
					.findByIdentifier(new AttributeIdentifier(attIdentifier));

			if (arg == null) {
				object.updateValue(attribute, null);
				return;
			}

			AbstractValue<?> value = valueFactory.create(arg);

			object.updateValue(attribute, value);
		}

		private void addValue(String attIdentifier, Method method, Object arg) {

			pl.softech.learning.domain.eav.Attribute attribute = attributeRepository
					.findByIdentifier(new AttributeIdentifier(attIdentifier));
			checkNotNull(arg);
			AbstractValue<?> value = valueFactory.create(arg);
			object.addValue(attribute, value);

		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

			String attIdentifier = getAttributeIdentifier(method);

			if (isGetter(method)) {

				Class<?> returnType = method.getReturnType();
				Object value = null;
				if (isCollection(returnType)) {
					value = getValues(attIdentifier, method);
				} else {
					value = getValue(attIdentifier, method);
				}

				if (value == null) {
					return null;
				}

				Class<?> valueType = value.getClass();
				checkState(returnType.isAssignableFrom(valueType), "Return type %s doesn't match attribute value type %s",
						returnType.getName(), valueType.getName());

				return value;

			}

			boolean isSetter = isSetter(method);
			boolean isAdd = isAdd(method);

			if (isSetter || isAdd) {

				if (isSetter) {

					setValue(attIdentifier, method, args[0]);

				} else {

					addValue(attIdentifier, method, args[0]);
				}

				return null;
			}

			throw new UnsupportedOperationException(String.format("Method %s is unsupported", method.getName()));

		}

	}

	@SuppressWarnings("unchecked")
	public <T> T frame(Class<T> clazz, MyObject object) {
		ClassLoader parentLoader = FrameFactory.class.getClassLoader();
		return (T) Proxy.newProxyInstance(parentLoader, new Class[] { clazz }, new MyInvocationHandler(object));
	}

}
