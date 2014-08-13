package pl.softech.learning.domain.eav;

import org.apache.commons.lang3.tuple.Pair;

import pl.softech.learning.domain.eav.Attribute.DataType;
import pl.softech.learning.domain.specification.Specification;

public class ValueMatchAttributeConstraint implements Specification<Pair<? extends AbstractValue<?>, Attribute>> {

	@Override
	public boolean isSafisfiedBy(final Pair<? extends AbstractValue<?>, Attribute> arg) {

		final Boolean[] bag = new Boolean[1];

		arg.getLeft().accept(new ValueVisitor() {

			private boolean checkDataType(DataType type) {
				return arg.getRight().getDataType() == type;
			}

			@Override
			public void visit(DateValue value) {
				bag[0] = checkDataType(DataType.DATE);
			}

			@Override
			public void visit(DictionaryEntryValue value) {
				bag[0] = checkDataType(DataType.DICTIONARY);
			}

			@Override
			public void visit(DoubleValue value) {
				bag[0] = checkDataType(DataType.DOUBLE);
			}

			@Override
			public void visit(IntegerValue value) {
				bag[0] = checkDataType(DataType.INTEGER);
			}

			@Override
			public void visit(BooleanValue value) {
				bag[0] = checkDataType(DataType.BOOLEAN);
			}

			@Override
			public void visit(StringValue value) {
				bag[0] = checkDataType(DataType.TEXT);
			}
		});

		return bag[0] == null ? false : bag[0];
	}

}
