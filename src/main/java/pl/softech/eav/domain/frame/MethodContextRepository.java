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

import java.lang.reflect.Method;
import java.util.Map;

import pl.softech.eav.domain.frame.MethodContextRepository.MethodContext.MethodType;

import com.google.common.collect.Maps;

/**
 * @author ssledz
 * @since 1.0
 */
public class MethodContextRepository {

	private final Class<?> clazz;

	private Map<Method, MethodContext> method2ctx = Maps.newHashMap();

	public MethodContextRepository(Class<?> clazz) {
		this.clazz = clazz;
		init();
	}

	private void init() {

		Map<String, MethodContext> prop2ctx = Maps.newHashMap();

		for (Method m : clazz.getMethods()) {

			boolean isGetter = ReflectionUtils.isGetter(m);
			boolean isSetter = ReflectionUtils.isSetter(m);
			boolean isMutable = isSetter || ReflectionUtils.isAdd(m);

			String propertName = ReflectionUtils.getPropertyName(m);

			if (!isGetter && !isMutable) {
				continue;
			}

			MethodContext ctx = new MethodContext(propertName, m, m.getAnnotation(Attribute.class), m.getAnnotation(Relation.class));
			method2ctx.put(m, ctx);

			if (isGetter) {
				ctx.setType(MethodType.GETTER);
			} else {
				ctx.setType(isSetter ? MethodType.SETTER : MethodType.ADD);
			}

			MethodContext last = prop2ctx.get(propertName);
			if (last != null) {
				last.merge(ctx);
			} else {
				prop2ctx.put(propertName, ctx);
			}

		}

	}

	public MethodContext findOne(Method method) {
		return method2ctx.get(method);
	}

	static class MethodContext {

		enum MethodType {
			GETTER, SETTER, ADD
		}

		private String propertyName;
		private Method method;
		private MethodType type;
		private Attribute att;
		private Relation rel;

		private MethodContext(String propertyName, Method method, Attribute att, Relation rel) {
			this.propertyName = propertyName;
			this.att = att;
			this.rel = rel;
		}

		private void setType(MethodType type) {
			this.type = type;
		}

		private void merge(MethodContext ctx) {
			
			if (ctx.att != null) {
				att = ctx.att;
			}

			if (ctx.rel != null) {
				rel = ctx.rel;
			}

		}

		public String getIdentifier() {

			if (att != null && att.name().length() > 0) {
				return att.name();
			}

			if (rel != null && rel.name().length() > 0) {
				return rel.name();
			}

			return propertyName.toLowerCase();
		}

		public String getPropertyName() {
			return propertyName;
		}

		public boolean isGetter() {
			return type == MethodType.GETTER;
		}

		public boolean isSetter() {
			return type == MethodType.SETTER;
		}
		
		public boolean isMutable() {
			return isSetter() || isAdd();
		}

		public boolean isAdd() {
			return type == MethodType.ADD;
		}

		public Method getMethod() {
			return method;
		}

		public Attribute getAtt() {
			return att;
		}

		public Relation getRel() {
			return rel;
		}

	}

}
