package pl.softech.learning.domain.eav.frame;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import pl.softech.learning.domain.eav.AttributeIdentifier;
import pl.softech.learning.domain.eav.AttributeRepository;
import pl.softech.learning.domain.eav.MyObject;
import pl.softech.learning.domain.eav.value.AbstractValue;
import pl.softech.learning.domain.eav.value.ObjectValue;
import pl.softech.learning.domain.eav.value.ValueVisitorAdapter;

/**
 * @author ssledz 
 */
public class FrameFactory {

	private AttributeRepository attributeRepository;

	public FrameFactory(AttributeRepository attributeRepository) {
		this.attributeRepository = attributeRepository;
	}

	private static class MyInvocationHandler implements InvocationHandler {

		private final MyObject object;

		public MyInvocationHandler(MyObject object) {
			this.object = checkNotNull(object);
		}

		private boolean isGetter(Method method, Object[] args) {
			if (args != null && args.length > 0) {
				return false;
			}
			return method.getName().startsWith("get");

		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

			if (isGetter(method, args)) {

				String identifier = method.getName().substring(3).toLowerCase();
				ObjectValue value = object.getValueByAttribute(new AttributeIdentifier(identifier));
				final Object[] bag = new Object[1];
				value.accept(new ValueVisitorAdapter() {
					@Override
					protected void visitAny(AbstractValue<?> value) {
						bag[0] = value.getValue();
					}
				});

				return bag[0];

			}

			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public <T> T frame(Class<T> clazz, MyObject object) {
		ClassLoader parentLoader = FrameFactory.class.getClassLoader();
		return (T) Proxy.newProxyInstance(parentLoader, new Class[] { clazz }, new MyInvocationHandler(object));
	}

}
