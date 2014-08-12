package pl.softech.learning.domain.eav;

import pl.softech.learning.domain.specification.Specification;

public class ObjectValueHasOneValueSetSpecification implements Specification<ObjectValue> {

	@Override
	public boolean isSafisfiedBy(ObjectValue arg) {

		final int[] bag = new int[1];

		arg.accept(new ValueVisitorAdapter() {
			@Override
			protected void visitAny(AbstractValue<?> value) {

				bag[0]++;

			}
		});

		return bag[0] == 1;
	}

}
