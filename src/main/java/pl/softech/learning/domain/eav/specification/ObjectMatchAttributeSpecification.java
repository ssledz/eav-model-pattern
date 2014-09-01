package pl.softech.learning.domain.eav.specification;

import org.apache.commons.lang3.tuple.Pair;

import pl.softech.learning.domain.eav.Attribute;
import pl.softech.learning.domain.eav.MyObject;
import pl.softech.learning.domain.specification.Specification;

public class ObjectMatchAttributeSpecification implements Specification<Pair<MyObject, Attribute>> {

	@Override
	public boolean isSafisfiedBy(Pair<MyObject, Attribute> arg) {

		return arg.getLeft().getCategory().getIdentifier().equals(arg.getRight().getCategory().getIdentifier());

	}

}
