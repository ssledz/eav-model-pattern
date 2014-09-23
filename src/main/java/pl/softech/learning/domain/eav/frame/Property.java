package pl.softech.learning.domain.eav.frame;

import java.lang.reflect.Method;

/**
 * @author ssledz
 */
public interface Property {

	void setValue(Method method, Object arg);

	void addValue(Method method, Object arg);

	Object getValues(Method method);

	Object getValue(Method method);

}
