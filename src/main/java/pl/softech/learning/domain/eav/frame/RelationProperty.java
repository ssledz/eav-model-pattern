package pl.softech.learning.domain.eav.frame;

import java.lang.reflect.Method;

import pl.softech.learning.domain.eav.MyObject;
import pl.softech.learning.domain.eav.relation.RelationConfigurationRepository;

/**
 * @author ssledz
 */
public class RelationProperty implements Property {

	private final MyObject object;

	private final RelationConfigurationRepository relationConfigurationRepository;
	
	private final String relationIdentifier;

	RelationProperty(MyObject object, String relationIdentifier, RelationConfigurationRepository relationConfigurationRepository) {
		this.object = object;
		this.relationIdentifier = relationIdentifier;
		this.relationConfigurationRepository = relationConfigurationRepository;
	}

	@Override
	public void setValue(Method method, Object arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addValue(Method method, Object arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getValues(Method method) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getValue(Method method) {
		// TODO Auto-generated method stub
		return null;
	}

}
