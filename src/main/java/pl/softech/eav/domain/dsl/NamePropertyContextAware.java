package pl.softech.eav.domain.dsl;

/**
 * @author ssledz
 * @since 1.0 
 */
public interface NamePropertyContextAware<T> {
	T withNamePropertyContext(NamePropertyContext ctx);
}
