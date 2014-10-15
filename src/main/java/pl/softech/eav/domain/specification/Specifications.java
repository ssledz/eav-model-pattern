package pl.softech.eav.domain.specification;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

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
