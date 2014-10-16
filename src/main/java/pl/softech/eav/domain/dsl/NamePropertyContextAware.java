package pl.softech.eav.domain.dsl;


public interface NamePropertyContextAware<T> {
	T withNamePropertyContext(NamePropertyContext ctx);
}
