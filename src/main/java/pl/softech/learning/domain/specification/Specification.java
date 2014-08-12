package pl.softech.learning.domain.specification;

public interface Specification<T> {

	boolean isSafisfiedBy(T arg);
	
}
