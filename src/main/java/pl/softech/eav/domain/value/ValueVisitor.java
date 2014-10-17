package pl.softech.eav.domain.value;

/**
 * @author ssledz
 * @since 1.0
 */
public interface ValueVisitor {

	void visit(StringValue value);

	void visit(BooleanValue value);

	void visit(IntegerValue value);

	void visit(DoubleValue value);

	void visit(DictionaryEntryValue value);

	void visit(DateValue value);
	
}
