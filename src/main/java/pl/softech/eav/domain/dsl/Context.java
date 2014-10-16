package pl.softech.eav.domain.dsl;

public interface Context {

	void accept(ContextVisitor visitor);
	
}
