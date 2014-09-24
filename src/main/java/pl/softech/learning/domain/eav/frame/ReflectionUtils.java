package pl.softech.learning.domain.eav.frame;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

public class ReflectionUtils {

	private static String capitalize(String word) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(Character.toUpperCase(word.charAt(0)));
		buffer.append(word.substring(1));
		return buffer.toString();

	}

	public static Method getSetter(String propertyName, Class<?> clazz, Class<?> arg) throws Exception {
		return clazz.getMethod(String.format("set", capitalize(propertyName)), arg);
	}

	public static Method getAdd(String propertyName, Class<?> clazz, Class<?> arg) throws Exception {
		return clazz.getMethod(String.format("add", capitalize(propertyName)), arg);
	}

	public static String getPropertyName(Method method) {
		String name = method.getName();

		for (String prefix : Arrays.asList("get", "is", "set", "add")) {
			if (name.startsWith(prefix)) {
				name = name.substring(prefix.length());
				break;
			}
		}

		return name;
	}

	public static Method getGetter(String propertyName, Class<?> clazz) throws Exception {

		String[] names = { String.format("get%s", capitalize(propertyName)),//
				String.format("is%s", capitalize(propertyName)) //
		};

		for (Method m : clazz.getMethods()) {

			for (String name : names) {
				if (m.getName().equals(name)) {
					return m;
				}
			}

		}

		return null;

	}

	public static boolean isGetter(Method method) {

		Class<?>[] args = method.getParameterTypes();

		if (args.length > 0) {
			return false;
		}

		String name = method.getName();

		return name.startsWith("get") || name.startsWith("is");
	}

	private static boolean isMutable(Method method, String prefix) {
		if (method.getReturnType() != void.class) {
			return false;
		}

		Class<?>[] args = method.getParameterTypes();

		if (args.length != 1) {
			return false;
		}

		return method.getName().startsWith(prefix);
	}

	public static boolean isSetter(Method method) {
		return isMutable(method, "set");
	}

	public static boolean isAdd(Method method) {
		return isMutable(method, "add");
	}

	public static boolean isCollection(Class<?> type) {
		return Collection.class.isAssignableFrom(type);
	}

	@SuppressWarnings("unchecked")
	public static Collection<Object> newCollection(Class<?> collType) throws Exception {
		
		if (collType.isInterface()) {

			if (collType.isAssignableFrom(ArrayList.class)) {

				return new ArrayList<>();

			} else if (collType.isAssignableFrom(HashSet.class)) {

				return new HashSet<>();

			}

		}

		return (Collection<Object>) collType.getConstructor(new Class<?>[] {}).newInstance();

	}

	public static void main(String[] args) throws Exception {
		System.out.println(isCollection(LinkedList.class));
		System.out.println(isCollection(ArrayList.class));
		System.out.println(isCollection(HashSet.class));

		System.out.println(ReflectionUtils.class.getMethod("main", new String[] {}.getClass()).getReturnType() == void.class);
	}
}
