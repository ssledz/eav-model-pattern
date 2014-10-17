/*
 * Copyright 2013 Sławomir Śledź <slawomir.sledz@sof-tech.pl>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.softech.eav.domain.frame;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static pl.softech.eav.domain.frame.ReflectionUtils.isCollection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import pl.softech.eav.domain.attribute.AttributeRepository;
import pl.softech.eav.domain.frame.MethodContextRepository.MethodContext;
import pl.softech.eav.domain.object.MyObject;
import pl.softech.eav.domain.relation.RelationConfigurationRepository;
import pl.softech.eav.domain.value.ValueFactory;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
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
				return new RelationProperty(object, ctx.getIdentifier(), relationConfigurationRepository, FrameFactory.this);
			}

			return new AttributeProperty(object, ctx.getIdentifier(), attributeRepository, valueFactory);
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

			if(method.getDeclaringClass().isAssignableFrom(MyObjectProxy.class)) {
				return object;
			}
			
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
		return (T) Proxy.newProxyInstance(parentLoader, new Class[] { clazz, MyObjectProxy.class }, new MyInvocationHandler(object, new MethodContextRepository(
				clazz)));
	}

}
