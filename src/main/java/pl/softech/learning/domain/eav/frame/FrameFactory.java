package pl.softech.learning.domain.eav.frame;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static pl.softech.learning.domain.eav.frame.ReflectionUtils.isCollection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import pl.softech.learning.domain.eav.AttributeRepository;
import pl.softech.learning.domain.eav.MyObject;
import pl.softech.learning.domain.eav.frame.MethodContextRepository.MethodContext;
import pl.softech.learning.domain.eav.relation.RelationConfigurationRepository;
import pl.softech.learning.domain.eav.value.ValueFactory;

/**
 * @author ssledz
 */
public class FrameFactory {

	private AttributeRepository attributeRepository;

	private RelationConfigurationRepository relationConfigurationRepository;

	private ValueFactory valueFactory = new ValueFactory();

	public FrameFactory(AttributeRepository attributeRepository, RelationConfigurationRepository relationConfigurationRepository) {
		this.attributeRepository = attributeRepository;
		this.relationConfigurationRepository = relationConfigurationRepository;
	}

	private class MyInvocationHandler implements InvocationHandler {

		private final MyObject object;
		private final MethodContextRepository methodContextRepository;

		public MyInvocationHandler(MyObject object, MethodContextRepository methodContextRepository) {
			this.object = checkNotNull(object);
			this.methodContextRepository = methodContextRepository;
		}

		private Property createProperty(MethodContext ctx) {

			if (ctx.getRel() != null) {
				return new RelationProperty(object, ctx.getIdentifier(), relationConfigurationRepository);
			}

			return new AttributeProperty(object, ctx.getIdentifier(), attributeRepository, valueFactory);
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

			MethodContext ctx = methodContextRepository.findOne(method);

			checkState(ctx != null, "Method %s is unsupported", method.getName());

			Property property = createProperty(ctx);

			if (ctx.isGetter()) {

				Class<?> returnType = method.getReturnType();
				Object value = null;

				if (isCollection(returnType)) {

					value = property.getValues(method);

				} else {

					value = property.getValue(method);

				}

				if (value == null) {
					return null;
				}

				Class<?> valueType = value.getClass();
				checkState(returnType.isAssignableFrom(valueType), "Return type %s doesn't match property value type %s",
						returnType.getName(), valueType.getName());

				return value;

			}

			if (ctx.isSetter()) {

				property.setValue(method, args[0]);

			} else {

				property.addValue(method, args[0]);

			}

			return null;

		}

	}

	@SuppressWarnings("unchecked")
	public <T> T frame(Class<T> clazz, MyObject object) {
		ClassLoader parentLoader = FrameFactory.class.getClassLoader();
		return (T) Proxy.newProxyInstance(parentLoader, new Class[] { clazz }, new MyInvocationHandler(object, new MethodContextRepository(
				clazz)));
	}

}
