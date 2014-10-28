/*
 * Copyright 2013 Sławomir Śledź <slawomir.sledz@sof-tech.pl>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.softech.eav.domain.dsl;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
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

	@Override
	public void visitOnEnter(RelationDefinitionContext ctx) {
		visitAny(ctx);
	}

	@Override
	public void visitOnLeave(RelationDefinitionContext ctx) {
		visitAny(ctx);
	}

	@Override
	public void visit(OwnerPropertyContext ctx) {
		visitAny(ctx);
	}

	@Override
	public void visit(TargetPropertyContext ctx) {
		visitAny(ctx);
	}

	@Override
	public void visit(RelationValueContext ctx) {
		visitAny(ctx);
	}

	@Override
	public void visitOnEnter(RelationSectionContext ctx) {
		visitAny(ctx);
	}

	@Override
	public void visitOnLeave(RelationSectionContext ctx) {
		visitAny(ctx);
	}

}
