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
import pl.softech.eav.domain.object.MyObject;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public class ObjectMatchAttributeSpecification implements Specification<Pair<MyObject, Attribute>> {

	@Override
	public boolean isSafisfiedBy(Pair<MyObject, Attribute> arg) {

		return arg.getLeft().getCategory().getIdentifier().equals(arg.getRight().getCategory().getIdentifier());

	}

}
