/*
 * Copyright 2013 Sławomir Śledź <slawomir.sledz@sof-tech.pl>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.softech.eav.domain.specification;

import org.apache.commons.lang3.tuple.Pair;

import pl.softech.eav.domain.attribute.Attribute;
import pl.softech.eav.domain.attribute.DataType.Type;
import pl.softech.eav.domain.dictionary.Dictionary;
import pl.softech.eav.domain.value.AbstractValue;
import pl.softech.eav.domain.value.BooleanValue;
import pl.softech.eav.domain.value.DateValue;
import pl.softech.eav.domain.value.DictionaryEntryValue;
import pl.softech.eav.domain.value.DoubleValue;
import pl.softech.eav.domain.value.IntegerValue;
import pl.softech.eav.domain.value.StringValue;
import pl.softech.eav.domain.value.ValueVisitor;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
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
