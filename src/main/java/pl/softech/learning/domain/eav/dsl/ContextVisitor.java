package pl.softech.learning.domain.eav.dsl;

public interface ContextVisitor {

	void visit(NamePropertyContext ctx);

	void visit(DataTypePropertyContext ctx);

	void visit(DictionaryDataTypePropertyContext ctx);

	void visit(CategoryPropertyContext ctx);

	void visitOnEnter(AttributeDefinitionContext ctx);

	void visitOnLeave(AttributeDefinitionContext ctx);

	void visitOnEnter(CategoryDefinitionContext ctx);

	void visitOnLeave(CategoryDefinitionContext ctx);

	void visitOnEnter(ObjectDefinitionContext ctx);

	void visitOnLeave(ObjectDefinitionContext ctx);

	void visit(AttributeValueContext ctx);

	void visitOnEnter(ObjectBodyContext ctx);

	void visitOnLeave(ObjectBodyContext ctx);

}
