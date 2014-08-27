package pl.softech.learning.domain.eav.dsl;

public class ContextVisitorAdapter implements ContextVisitor {

	@Override
	public void visit(NamePropertyContext ctx) {
		visitAny(ctx);
	}

	@Override
	public void visit(DataTypePropertyContext ctx) {
		visitAny(ctx);
	}

	@Override
	public void visit(DictionaryDataTypePropertyContext ctx) {
		visitAny(ctx);
	}

	@Override
	public void visit(CategoryPropertyContext ctx) {
		visitAny(ctx);
	}

	@Override
	public void visitOnEnter(AttributeDefinitionContext ctx) {
		visitAny(ctx);
	}

	@Override
	public void visitOnLeave(AttributeDefinitionContext ctx) {
		visitAny(ctx);
	}

	@Override
	public void visitOnEnter(CategoryDefinitionContext ctx) {
		visitAny(ctx);
	}

	@Override
	public void visitOnLeave(CategoryDefinitionContext ctx) {
		visitAny(ctx);
	}

	@Override
	public void visitOnEnter(ObjectDefinitionContext ctx) {
		visitAny(ctx);
	}

	@Override
	public void visitOnLeave(ObjectDefinitionContext ctx) {
		visitAny(ctx);
	}

	protected void visitAny(Context ctx) {
	}

	@Override
	public void visit(AttributeValueContext ctx) {
		visitAny(ctx);
	}

	@Override
	public void visitOnEnter(ObjectBodyContext ctx) {
		visitAny(ctx);
	}

	@Override
	public void visitOnLeave(ObjectBodyContext ctx) {
		visitAny(ctx);
	}

}
