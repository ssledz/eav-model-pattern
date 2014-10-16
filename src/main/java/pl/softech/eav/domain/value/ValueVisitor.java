package pl.softech.eav.domain.value;

/**
 * @author ssledz
 */
public interface ValueVisitor {

	void visit(StringValue value);

	void visit(BooleanValue value);

	void visit(IntegerValue value);

	void visit(DoubleValue value);

	void visit(DictionaryEntryValue value);

	void visit(DateValue value);
	
}
