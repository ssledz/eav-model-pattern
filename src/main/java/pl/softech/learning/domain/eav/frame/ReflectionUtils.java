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
	
	public static boolean isCollection(Class<?> type) {
		return Collection.class.isAssignableFrom(type);
	}

	
	public static void main(String[] args) {
		System.out.println(isCollection(LinkedList.class));
		System.out.println(isCollection(ArrayList.class));
		System.out.println(isCollection(HashSet.class));
	}
}
