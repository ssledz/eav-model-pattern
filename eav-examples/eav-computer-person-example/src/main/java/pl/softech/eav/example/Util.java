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

	private static final Function<DictionaryEntry, String> DICT_2_STRING = new Function<DictionaryEntry, String>() {

		@Override
		public String apply(DictionaryEntry input) {
			return input.getName();
		}
	};

	private static String toString(DictionaryEntry entry) {
		return entry == null ? null : DICT_2_STRING.apply(entry);
	}

	private static Collection<String> toString(Collection<DictionaryEntry> entries) {
		return entries == null ? null : Collections2.transform(entries, DICT_2_STRING);
	}

	private static String toString(ObjectValue vakue, int ntabs) {
		if (vakue == null) {
			return null;
		}
		SimpleToStringBuilder sb = new SimpleToStringBuilder("ObjectValue", ntabs);
		sb.add("attribute", vakue.getAttribute().getName());
		sb.add("value", vakue.getValueAsString());
		return sb.toString();
	}

	private static String toString(MyObject object, final int ntabs) {
		if (object == null) {
			return null;
		}

		SimpleToStringBuilder sb = new SimpleToStringBuilder("MyObject", ntabs);
		sb.add("name", object.getName());
		sb.add("category", object.getCategory().getIdentifier().getIdentifier());
		sb.add("values", collectionToString(Collections2.transform(object.getValues(), new Function<ObjectValue, String>() {
			@Override
			public String apply(ObjectValue input) {
				return Util.toString(input, ntabs + 2);
			}
		}), ntabs + 1));
		return sb.toString();
	}

	private static String toString(Computer computer, int ntabs) {
		if (computer == null) {
			return null;
		}
		SimpleToStringBuilder sb = new SimpleToStringBuilder("Computer", ntabs);
		sb.add("Make", Util.toString(computer.getMake()));
		sb.add("drive", computer.getDrive());
		sb.add("battery", computer.getBattery());
		sb.add("cpu", computer.getCpu());
		sb.add("model", computer.getModel());
		sb.add("optical", computer.getOptical());
		sb.add("os", Util.toString(computer.getOs()));
		sb.add("purshaseDate", computer.getPurshaseDate());
		sb.add("ram", computer.getRam());
		sb.add("screen", computer.getScreen());
		sb.add("video", computer.getVideo());
		sb.add("type", Util.toString(computer.getType()));
		return sb.toString();
	}

	private static String toStringShort(Person person, int ntabs) {
		SimpleToStringBuilder builder = new SimpleToStringBuilder("Person", ntabs);
		builder.add("firstname", person.getFirstname());
		builder.add("lastname", person.getLastname());
		builder.add("age", person.getAge());
		return builder.toString();
	}

	public static String toString(Person person) {
		return toString(person, 1);
	}

	public static String toString(Person person, final int ntabs) {
		SimpleToStringBuilder builder = new SimpleToStringBuilder("Person", ntabs);
		builder.add("firstname", person.getFirstname());
		builder.add("lastname", person.getLastname());
		builder.add("age", person.getAge());
		if (person.getComputer() != null) {
			builder.add("Computer", "\n" + ntabs(ntabs + 1) + Util.toString(person.getComputer(), ntabs + 2));
		}
		if (person.getParent() != null) {
			builder.add("parent", "\n" + ntabs(ntabs + 1) + Util.toString(person.getParent(), ntabs + 2));
		}
		if (person.getFriends() != null) {
			builder.add("friends", collectionToString(Collections2.transform(person.getFriends(), new Function<Person, String>() {

				@Override
				public String apply(Person input) {
					return Util.toStringShort(input, ntabs + 2);
				}
			}), ntabs + 1));
		}
		return builder.toString();

	}

	private static String collectionToString(Collection<String> args, int ntabs) {
		String t = ntabs(ntabs);
		StringBuilder builder = new StringBuilder();
		for (Object obj : args) {
			builder.append("\n").append(t).append(obj.toString());
		}
		return builder.toString();
	}

	private static final String TAB = "   ";

	private static String ntabs(int ntabs) {
		String t = "";
		for (int i = 0; i < ntabs; i++) {
			t += TAB;
		}
		return t;
	}

	private static class SimpleToStringBuilder {

		private final StringBuilder builder = new StringBuilder();

		private String nlt = "\n";

		public SimpleToStringBuilder(String className, int ntabs) {
			builder.append("[").append(className).append("]");
			nlt += ntabs(ntabs);
		}

		public SimpleToStringBuilder add(String name, Object value) {
			builder.append(nlt).append(name).append(" = ").append(value);
			return this;
		}

		@Override
		public String toString() {
			return builder.toString();
		}
	}
}
