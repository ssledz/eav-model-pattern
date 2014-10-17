package pl.softech.eav.domain.dsl;

/**
 * @author ssledz
 * @since 1.0 
 */
public interface Context {

	void accept(ContextVisitor visitor);
	
}
