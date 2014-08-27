package pl.softech.learning.domain.eav.dsl;


public interface NamePropertyContextAware<T> {
	T withNamePropertyContext(NamePropertyContext ctx);
}
