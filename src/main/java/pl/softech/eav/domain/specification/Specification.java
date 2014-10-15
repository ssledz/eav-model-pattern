package pl.softech.eav.domain.specification;

public interface Specification<T> {

	boolean isSafisfiedBy(T arg);
	
}
