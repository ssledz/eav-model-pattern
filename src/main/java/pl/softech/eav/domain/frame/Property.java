package pl.softech.eav.domain.frame;

import java.lang.reflect.Method;

/**
 * @author ssledz
 * @since 1.0
 */
public interface Property {

	void setValue(Method method, Object arg);

	void addValue(Method method, Object arg);

	Object getValues(Method method);

	Object getValue(Method method);

}
