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
package pl.softech.eav.example;

import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import pl.softech.eav.domain.dictionary.DictionaryEntry;
import pl.softech.eav.domain.object.MyObject;
import pl.softech.eav.domain.value.ObjectValue;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.2
 */
public class Util {
	private static final Function<ObjectValue, String> OBJECT_VALUE_2_STRING = new Function<ObjectValue, String>() {

		@Override
		public String apply(ObjectValue input) {
			return Util.toString(input);
		}
	};

	private static final Function<DictionaryEntry, String> DICT_2_STRING = new Function<DictionaryEntry, String>() {

		@Override
		public String apply(DictionaryEntry input) {
			return input.getName();
		}
	};

	private static final Function<Person, String> PERSON_2_STRING = new Function<Person, String>() {

		@Override
		public String apply(Person input) {
			return Util.toStringShort(input);
		}
	};

	private static String toString(DictionaryEntry entry) {
		return entry == null ? null : DICT_2_STRING.apply(entry);
	}

	private static Collection<String> toString(Collection<DictionaryEntry> entries) {
		return entries == null ? null : Collections2.transform(entries, DICT_2_STRING);
	}

	private static String toString(ObjectValue vakue) {
		if (vakue == null) {
			return null;
		}
		ToStringBuilder sb = new ToStringBuilder(vakue, ToStringStyle.SHORT_PREFIX_STYLE);
		sb.append("attribute", vakue.getAttribute().getName());
		sb.append("value", vakue.getValueAsString());
		return sb.toString();
	}

	private static String toString(MyObject object) {
		if (object == null) {
			return null;
		}
		ToStringBuilder sb = new ToStringBuilder(object, ToStringStyle.SHORT_PREFIX_STYLE);
		sb.append("name", object.getName());
		sb.append("category", object.getCategory().getIdentifier().getIdentifier());
		sb.append("values", Collections2.transform(object.getValues(), OBJECT_VALUE_2_STRING));
		return sb.toString();
	}

	private static String toString(Computer computer) {
		if (computer == null) {
			return null;
		}
		ToStringBuilder sb = new ToStringBuilder(computer, ToStringStyle.SHORT_PREFIX_STYLE);
		sb.append("Make", Util.toString(computer.getMake()));
		sb.append("drive", computer.getDrive());
		sb.append("battery", computer.getBattery());
		sb.append("cpu", computer.getCpu());
		sb.append("model", computer.getModel());
		sb.append("optical", computer.getOptical());
		sb.append("os", Util.toString(computer.getOs()));
		sb.append("purshaseDate", computer.getPurshaseDate());
		sb.append("ram", computer.getRam());
		sb.append("screen", computer.getScreen());
		sb.append("video", computer.getVideo());
		sb.append("type", Util.toString(computer.getType()));
		return sb.toString();
	}

	private static String toStringShort(Person person) {
		ToStringBuilder sb = new ToStringBuilder(person, ToStringStyle.SHORT_PREFIX_STYLE);
		sb.append("firstname", person.getFirstname());
		sb.append("lastname", person.getLastname());
		sb.append("age", person.getAge());
		return sb.toString();
	}

	public static String toString(Person person) {
		ToStringBuilder sb = new ToStringBuilder(person, ToStringStyle.MULTI_LINE_STYLE);
		sb.append("firstname", person.getFirstname());
		sb.append("lastname", person.getLastname());
		sb.append("age", person.getAge());
		sb.append("Computer", Util.toString(person.getComputer()));
		sb.append("parent", Util.toString(person.getParent()));
		sb.append("friends", Collections2.transform(person.getFriends(), PERSON_2_STRING));
		return sb.toString();
	}
}
