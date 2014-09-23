package pl.softech.learning.domain.eav.frame;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.Method;

import pl.softech.learning.domain.eav.MyObject;
import pl.softech.learning.domain.eav.relation.Relation;
import pl.softech.learning.domain.eav.relation.RelationConfiguration;
import pl.softech.learning.domain.eav.relation.RelationConfigurationRepository;
import pl.softech.learning.domain.eav.relation.RelationIdentifier;

/**
 * @author ssledz
 */
public class RelationProperty implements Property {

	private final MyObject object;

	private final RelationConfigurationRepository relationConfigurationRepository;

	private final String relationIdentifier;
	
	private final FrameFactory frameFactory;

	RelationProperty(MyObject object, String relationIdentifier, RelationConfigurationRepository relationConfigurationRepository, FrameFactory frameFactory) {
		this.object = object;
		this.relationIdentifier = relationIdentifier;
		this.relationConfigurationRepository = relationConfigurationRepository;
		this.frameFactory = frameFactory;
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

		RelationConfiguration conf = relationConfigurationRepository.findByIdentifier(new RelationIdentifier(relationIdentifier));
		checkNotNull(conf);

		Relation r = object.getRelationByIdentifier(conf.getIdentifier());

		if(r == null || r.getTarget() == null) {
			return null;
		}
		
		Class<?> returnType = method.getReturnType();
		
		if(returnType.equals(MyObject.class)) {
			return r.getTarget();
		}
		
		return frameFactory.frame(returnType, r.getTarget());
	}

}
