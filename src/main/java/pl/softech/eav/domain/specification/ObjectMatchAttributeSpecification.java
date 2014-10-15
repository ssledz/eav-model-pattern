package pl.softech.eav.domain.specification;

import org.apache.commons.lang3.tuple.Pair;

import pl.softech.eav.domain.attribute.Attribute;
import pl.softech.eav.domain.object.MyObject;

public class ObjectMatchAttributeSpecification implements Specification<Pair<MyObject, Attribute>> {

	@Override
	public boolean isSafisfiedBy(Pair<MyObject, Attribute> arg) {

		return arg.getLeft().getCategory().getIdentifier().equals(arg.getRight().getCategory().getIdentifier());

	}

}
