package pl.softech.eav.domain.value;

public class ValueVisitorAdapter implements ValueVisitor {

	protected void visitAny(AbstractValue<?> value) {
	}

	@Override
	public void visit(StringValue value) {
		visitAny(value);
	}

	@Override
	public void visit(BooleanValue value) {
		visitAny(value);
	}

	@Override
	public void visit(IntegerValue value) {
		visitAny(value);
	}

	@Override
	public void visit(DoubleValue value) {
		visitAny(value);
	}

	@Override
	public void visit(DictionaryEntryValue value) {
		visitAny(value);
	}

	@Override
	public void visit(DateValue value) {
		visitAny(value);
	}

}
