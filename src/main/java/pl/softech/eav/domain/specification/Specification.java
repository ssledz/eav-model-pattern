package pl.softech.eav.domain.specification;

/**
 * @author ssledz
 */
public interface Specification<T> {

	boolean isSafisfiedBy(T arg);
	
}
