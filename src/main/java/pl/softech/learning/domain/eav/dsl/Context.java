package pl.softech.learning.domain.eav.dsl;

public interface Context {

	void accept(ContextVisitor visitor);
	
}
