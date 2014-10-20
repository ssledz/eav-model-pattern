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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public class Specifications {

	@SuppressWarnings("unchecked")
	public static <T> Specification<T> and(final Specification<T>... specifications) {
		checkNotNull(specifications);
		checkArgument(specifications.length > 1, "There must be at least 2 specifications");
		return new Specification<T>() {

			@Override
			public boolean isSafisfiedBy(T t) {
				for (Specification<T> s : specifications) {
					if (!s.isSafisfiedBy(t)) {
						return false;
					}
				}
				return true;
			}
		};
	}

	@SuppressWarnings("unchecked")
	public static <T> Specification<T> or(final Specification<T>... specifications) {
		checkNotNull(specifications);
		checkArgument(specifications.length > 1, "There must be at least 2 specifications");
		return new Specification<T>() {

			@Override
			public boolean isSafisfiedBy(T t) {
				for (Specification<T> s : specifications) {
					if (s.isSafisfiedBy(t)) {
						return true;
					}
				}
				return false;
			}
		};

	}

	public static <T> Specification<T> not(final Specification<T> specification) {
		checkNotNull(specification);
		return new Specification<T>() {

			@Override
			public boolean isSafisfiedBy(T t) {
				return !specification.isSafisfiedBy(t);
			}
		};
	}

	public static <T> List<T> filter(List<T> elements, final Specification<T> specification) {
		checkNotNull(specification);
		checkNotNull(elements);
		return Lists.newArrayList(Iterables.filter(elements, new Predicate<T>() {

			@Override
			public boolean apply(T input) {
				return specification.isSafisfiedBy(input);
			}
		}));
	}

}
