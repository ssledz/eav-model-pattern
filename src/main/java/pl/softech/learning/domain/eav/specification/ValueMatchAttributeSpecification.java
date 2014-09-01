package pl.softech.learning.domain.eav.specification;

import org.apache.commons.lang3.tuple.Pair;

import pl.softech.learning.domain.dictionary.Dictionary;
import pl.softech.learning.domain.eav.Attribute;
import pl.softech.learning.domain.eav.DataType;
import pl.softech.learning.domain.eav.DataType.Type;
import pl.softech.learning.domain.eav.value.AbstractValue;
import pl.softech.learning.domain.eav.value.BooleanValue;
import pl.softech.learning.domain.eav.value.DateValue;
import pl.softech.learning.domain.eav.value.DictionaryEntryValue;
import pl.softech.learning.domain.eav.value.DoubleValue;
import pl.softech.learning.domain.eav.value.IntegerValue;
import pl.softech.learning.domain.eav.value.StringValue;
import pl.softech.learning.domain.eav.value.ValueVisitor;
import pl.softech.learning.domain.specification.Specification;

public class ValueMatchAttributeSpecification implements Specification<Pair<? extends AbstractValue<?>, Attribute>> {

	@Override
	public boolean isSafisfiedBy(final Pair<? extends AbstractValue<?>, Attribute> arg) {

		final Boolean[] bag = new Boolean[1];

		arg.getLeft().accept(new ValueVisitor() {

			private boolean checkDataType(Type type) {
				return arg.getRight().getDataType().getType() == type;
			}

			private boolean checkDictionary(Dictionary left, Dictionary right) {
				return left.getIdentifier().equals(right.getIdentifier());
			}

			@Override
			public void visit(DateValue value) {
				bag[0] = checkDataType(Type.DATE);
			}

			@Override
			public void visit(DictionaryEntryValue value) {
				bag[0] = checkDataType(Type.DICTIONARY);
				bag[0] &= checkDictionary(value.getValue().getDictionary(), arg.getRight().getDataType().getDictionary());
			}

			@Override
			public void visit(DoubleValue value) {
				bag[0] = checkDataType(Type.DOUBLE);
			}

			@Override
			public void visit(IntegerValue value) {
				bag[0] = checkDataType(Type.INTEGER);
			}

			@Override
			public void visit(BooleanValue value) {
				bag[0] = checkDataType(Type.BOOLEAN);
			}

			@Override
			public void visit(StringValue value) {
				bag[0] = checkDataType(Type.TEXT);
			}
		});

		return bag[0] == null ? false : bag[0];
	}

}
