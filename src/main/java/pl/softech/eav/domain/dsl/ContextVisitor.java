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
 * @author ssledz
 * @since 1.0 
 */
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

	void visitOnEnter(RelationDefinitionContext ctx);

	void visitOnLeave(RelationDefinitionContext ctx);

	void visit(OwnerPropertyContext ctx);
	
	void visit(TargetPropertyContext ctx);

	void visit(RelationValueContext ctx);

	void visitOnEnter(RelationSectionContext ctx);

	void visitOnLeave(RelationSectionContext ctx);

}
