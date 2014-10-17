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
package pl.softech.eav.domain.value;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import pl.softech.eav.domain.dictionary.DictionaryEntry;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
@Embeddable
public class DictionaryEntryValue extends AbstractValue<DictionaryEntry>{

	@ManyToOne(fetch = FetchType.LAZY)
	private DictionaryEntry value;

	protected DictionaryEntryValue() {
	}

	public DictionaryEntryValue(DictionaryEntry value) {
		this.value = checkNotNull(value);
	}

	@Override
	public DictionaryEntry getValue() {
		return value;
	}

	@Override
	public void accept(ValueVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String asString() {
		return getValue() != null ? getValue().getName() : "null";
	}
	
}
