package pl.softech.eav.domain.specification;

/**
 * @author ssledz
 * @since 1.0
 */
public interface Specification<T> {

	boolean isSafisfiedBy(T arg);
	
}
