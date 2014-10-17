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

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;

import pl.softech.eav.domain.attribute.Attribute;
import pl.softech.eav.domain.attribute.DataType;
import pl.softech.eav.domain.attribute.DataTypeSerialisationService;
import pl.softech.eav.domain.category.Category;
import pl.softech.eav.domain.dictionary.Dictionary;
import pl.softech.eav.domain.dictionary.DictionaryIdentifier;
import pl.softech.eav.domain.dictionary.DictionaryRepository;
import pl.softech.eav.domain.object.MyObject;
import pl.softech.eav.domain.relation.RelationConfiguration;
import pl.softech.eav.domain.value.AbstractValue;

/**
 * @author ssledz
 * @since 1.0
 */
public class CreateModelVisitor implements ContextVisitor {

	private SymbolTable symbolTable = new DefaultSymbolTable();

	private Category.Builder currentCategorBuilder;

	private Attribute.Builder currentAttributeBuilder;

	private MyObject.Builder currentObjectBuilder;

	private RelationConfiguration.Builder currentRelationBuilder;

	private final DictionaryRepository dictionaryRepository;

	private final DataTypeSerialisationService dataTypeSerialisationService;

	public CreateModelVisitor(DictionaryRepository dictionaryRepository, DataTypeSerialisationService dataTypeSerialisationService) {
		this.dictionaryRepository = dictionaryRepository;
		this.dataTypeSerialisationService = dataTypeSerialisationService;
	}

	public void setSymbolTable(SymbolTable symbolTable) {
		this.symbolTable = symbolTable;
	}

	public Collection<Category> getCategories() {
		return symbolTable.getCategories();
	}

	public Collection<Attribute> getAttributes() {
		return symbolTable.getAttributes();
	}

	public Collection<RelationConfiguration> getRelations() {
		return symbolTable.getRelations();
	}

	public Collection<MyObject> getObjects() {
		return symbolTable.getObjects();
	}

	@Override
	public void visitOnEnter(AttributeDefinitionContext ctx) {
		currentAttributeBuilder = new Attribute.Builder().withIdentifier(ctx.getIdentifier());
	}

	@Override
	public void visit(CategoryPropertyContext ctx) {
		Category category = checkNotNull(symbolTable.getCategory(ctx.getCategoryName()), "No category with identifier %s",
				ctx.getCategoryName());
		currentAttributeBuilder.withCategory(category);
	}

	@Override
	public void visit(NamePropertyContext ctx) {

		if (currentAttributeBuilder != null) {

			currentAttributeBuilder.withName(ctx.getName());

		} else if (currentCategorBuilder != null) {

			currentCategorBuilder.withName(ctx.getName());

		} else if (currentObjectBuilder != null) {

			currentObjectBuilder.withName(ctx.getName());

		} else if (currentRelationBuilder != null) {

			currentRelationBuilder.withName(ctx.getName());

		}

		else {

			throw new IllegalStateException("Name propery without context");

		}
	}

	@Override
	public void visit(DataTypePropertyContext ctx) {
		currentAttributeBuilder.withDataType(new DataType.Builder().withType(ctx.getDataType()));
	}

	@Override
	public void visit(DictionaryDataTypePropertyContext ctx) {
		Dictionary dictionary = dictionaryRepository.findByIdentifier(new DictionaryIdentifier(ctx.getDictionaryIdentifier()));
		currentAttributeBuilder.withDataType(new DataType.Builder().withType(DataType.Type.DICTIONARY).withDictionary(dictionary));
	}

	@Override
	public void visitOnLeave(AttributeDefinitionContext ctx) {
		symbolTable.put(ctx.getIdentifier(), new Attribute(currentAttributeBuilder));
		currentAttributeBuilder = null;
	}

	@Override
	public void visitOnEnter(CategoryDefinitionContext ctx) {
		currentCategorBuilder = new Category.Builder().withIdentifier(ctx.getIdentifier());
	}

	@Override
	public void visitOnLeave(CategoryDefinitionContext ctx) {
		symbolTable.put(ctx.getIdentifier(), new Category(currentCategorBuilder));
		currentCategorBuilder = null;
	}

	@Override
	public void visitOnEnter(ObjectDefinitionContext ctx) {

		Category category = checkNotNull(symbolTable.getCategory(ctx.getCategoryIdentifier()), "No category with identifier %s",
				ctx.getCategoryIdentifier());

		currentObjectBuilder = new MyObject.Builder().withCategory(category);
	}

	@Override
	public void visitOnLeave(ObjectDefinitionContext ctx) {
		symbolTable.put(ctx.getObjectIdentifier(), new MyObject(currentObjectBuilder));
		currentObjectBuilder = null;

	}

	@Override
	public void visitOnEnter(ObjectBodyContext ctx) {
	}

	@Override
	public void visit(AttributeValueContext ctx) {

		Attribute attribute = checkNotNull(symbolTable.getAttribute(ctx.getAttributeIdentifier()), "No attribute with identifier %s",
				ctx.getAttributeIdentifier());
		AbstractValue<?> value = dataTypeSerialisationService.readValue(attribute.getDataType().getType(), ctx.getValue());

		currentObjectBuilder.add(attribute, value);

	}

	@Override
	public void visitOnLeave(ObjectBodyContext ctx) {
	}

	@Override
	public void visitOnEnter(RelationDefinitionContext ctx) {

		currentRelationBuilder = new RelationConfiguration.Builder();
		currentRelationBuilder.withIdentifier(ctx.getRelationIdentifier());

	}

	@Override
	public void visitOnLeave(RelationDefinitionContext ctx) {

		symbolTable.put(ctx.getRelationIdentifier(), new RelationConfiguration(currentRelationBuilder));
		currentRelationBuilder = null;

	}

	@Override
	public void visit(OwnerPropertyContext ctx) {

		Category owner = checkNotNull(symbolTable.getCategory(ctx.getName()), "No category with identifier %s", ctx.getName());
		currentRelationBuilder.withOwner(owner);

	}

	@Override
	public void visit(TargetPropertyContext ctx) {

		Category target = checkNotNull(symbolTable.getCategory(ctx.getName()), "No category with identifier %s", ctx.getName());
		currentRelationBuilder.withTarget(target);

	}

	@Override
	public void visit(RelationValueContext ctx) {
		RelationConfiguration relation = checkNotNull(symbolTable.getRelation(ctx.getRelationIdentifier()),
				"No relation with identifier %s", ctx.getRelationIdentifier());
		MyObject target = checkNotNull(symbolTable.getObject(ctx.getObjectIdentifier()), "No object with identifier %s",
				ctx.getObjectIdentifier());
		currentObjectBuilder.add(relation, target);

	}

	@Override
	public void visitOnEnter(RelationSectionContext ctx) {

	}

	@Override
	public void visitOnLeave(RelationSectionContext ctx) {

	}

}
