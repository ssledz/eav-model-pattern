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
package pl.softech.eav.domain.attribute;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import pl.softech.eav.domain.AbstractValueObject;
import pl.softech.eav.domain.dictionary.Dictionary;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
@Embeddable
public class DataType extends AbstractValueObject {

	public enum Type {
		TEXT, DOUBLE, INTEGER, DICTIONARY, BOOLEAN, DATE
	}

	@Enumerated(EnumType.STRING)
	private Type type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dictionary_id")
	private Dictionary dictionary;

	protected DataType() {
	}

	public DataType(Builder builder) {
		this.type = checkNotNull(builder.type);
		this.dictionary = builder.dictionary;
		if (type != Type.DICTIONARY) {
			checkArgument(dictionary == null);
		} else {
			checkArgument(dictionary != null);
		}
	}

	public DataType(Type type) {
		this.type = checkNotNull(type);
		checkArgument(type != Type.DICTIONARY);
	}

	public DataType(Dictionary dictionary) {
		this.type = Type.DICTIONARY;
		this.dictionary = checkNotNull(dictionary);
	}

	public Type getType() {
		return type;
	}

	public Dictionary getDictionary() {
		return dictionary;
	}

	public static class Builder {

		private Type type;
		private Dictionary dictionary;

		public Builder withType(Type type) {
			this.type = type;
			return this;
		}

		public Builder withType(String type) {
			return withType(Type.valueOf(type.toUpperCase()));
		}

		public Builder withDictionary(Dictionary dictionary) {
			this.dictionary = dictionary;
			return this;
		}

	}

}
