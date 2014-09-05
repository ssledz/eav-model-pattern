package pl.softech.learning.domain.eav.frame;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

public class ReflectionUtils {

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

	public static void main(String[] args) throws Exception {
		System.out.println(isCollection(LinkedList.class));
		System.out.println(isCollection(ArrayList.class));
		System.out.println(isCollection(HashSet.class));

		System.out.println(ReflectionUtils.class.getMethod("main", new String[] {}.getClass()).getReturnType() == void.class);
	}
}
