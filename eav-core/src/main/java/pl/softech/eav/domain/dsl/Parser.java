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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import pl.softech.eav.domain.dsl.AttributeDefinitionContext.Builder;
import pl.softech.eav.domain.dsl.Token.Type;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public class Parser {

	private Lexer lexer;
	private Token currentToken;
	private final ContextVisitor contextVisitor;

	public Parser(ContextVisitor contextVisitor) {
		this.contextVisitor = contextVisitor;
	}

	private boolean match(Type type) {
		return type == currentToken.getType();
	}

	private void consume(Type type) {
		if (!match(type)) {
			throw new RuntimeException(String.format("Should be %s is %s [%d]", type.name(), currentToken.getType().name(), currentToken.getLineNumber()));
		}
		currentToken = lexer.next();
	}

	public void parse(InputStreamReader in) throws IOException {

		StringBuffer buffer = new StringBuffer();
		try (BufferedReader bin = new BufferedReader(in)) {
			String line;

			while ((line = bin.readLine()) != null) {
				buffer.append(line).append("\n");
			}
		}

		parse(buffer.toString());
	}

	public void parse(String in) {
		lexer = new Lexer(in);
		currentToken = lexer.next();
		conf();
	}

	private void conf() {

		while (!match(Type.EOF)) {

			if (match(Type.CATEGORY)) {
				catDef();
			} else if (match(Type.ATTRIBUTE)) {
				attDef();
			} else if (match(Type.RELATION)) {
				relDef();
			} else {
				objDef();
			}

		}

		consume(Type.EOF);

	}

	private void relDef() {
		consume(Type.RELATION);
		RelationDefinitionContext.Builder builder = new RelationDefinitionContext.Builder();
		builder.withRelationIdentifier(currentToken.getValue());
		consume(Type.IDENTIFIER);

		while (!match(Type.END)) {

			if (match(Type.NAME)) {
				nameProperty(builder);
			} else if (match(Type.OWNER)) {
				ownerProperty(builder);
			} else {
				targetProperty(builder);
			}

		}

		consume(Type.END);

		new RelationDefinitionContext(builder).accept(contextVisitor);
	}

	private void ownerProperty(RelationDefinitionContext.Builder builder) {
		consume(Type.OWNER);
		consume(Type.COLON);
		builder.addContext(new OwnerPropertyContext(currentToken.getValue()));
		consume(Type.STRING);
	}

	private void targetProperty(RelationDefinitionContext.Builder builder) {
		consume(Type.TARGET);
		consume(Type.COLON);
		builder.addContext(new TargetPropertyContext(currentToken.getValue()));
		consume(Type.STRING);
	}

	private void catDef() {
		consume(Type.CATEGORY);

		CategoryDefinitionContext.Builder builder = new CategoryDefinitionContext.Builder();
		builder.withIdentifier(currentToken.getValue());
		consume(Type.IDENTIFIER);

		nameProperty(builder);

		consume(Type.END);

		new CategoryDefinitionContext(builder).accept(contextVisitor);
	}

	private void attDef() {
		consume(Type.ATTRIBUTE);

		AttributeDefinitionContext.Builder builder = new AttributeDefinitionContext.Builder();

		builder.withIdentifier(currentToken.getValue());
		consume(Type.IDENTIFIER);

		while (!match(Type.END)) {

			if (match(Type.NAME)) {
				nameProperty(builder);
			} else if (match(Type.CATEGORY)) {
				categoryProperty(builder);
			} else {
				dataTypeProperty(builder);
			}

		}

		consume(Type.END);

		new AttributeDefinitionContext(builder).accept(contextVisitor);

	}

	private void dataTypeProperty(Builder builder) {
		consume(Type.DATA_TYPE);
		consume(Type.COLON);

		if (match(Type.DICTIONARY)) {

			consume(Type.DICTIONARY);
			consume(Type.OF);
			builder.withDataTypePropertyContext(new DictionaryDataTypePropertyContext(currentToken.getValue()));
			consume(Type.STRING);

		} else {

			builder.withDataTypePropertyContext(new DataTypePropertyContext(currentToken.getValue()));
			consume(Type.IDENTIFIER);

		}

	}

	private void categoryProperty(Builder builder) {
		consume(Type.CATEGORY);
		consume(Type.COLON);
		builder.withCategoryPropertyContext(new CategoryPropertyContext(currentToken.getValue()));
		consume(Type.STRING);
	}

	private void nameProperty(NamePropertyContextAware<?> builder) {
		consume(Type.NAME);
		consume(Type.COLON);
		builder.withNamePropertyContext(new NamePropertyContext(currentToken.getValue()));
		consume(Type.STRING);
	}

	private void objDef() {
		consume(Type.OBJECT);

		ObjectDefinitionContext.Builder builder = new ObjectDefinitionContext.Builder();
		builder.withObjectIdentifier(currentToken.getValue());
		consume(Type.IDENTIFIER);
		consume(Type.OF);
		builder.withCategoryIdentifier(currentToken.getValue());
		consume(Type.IDENTIFIER);
		consume(Type.CATEGORY);

		objBody(builder);

		consume(Type.END);

		new ObjectDefinitionContext(builder).accept(contextVisitor);
	}

	private void objBody(ObjectDefinitionContext.Builder builder) {
		ObjectBodyContext.Builder innerBuilder = new ObjectBodyContext.Builder();

		while (!match(Type.END)) {

			if (match(Type.NAME)) {
				nameProperty(innerBuilder);
			} else if (match(Type.RELATIONS)) {
				relSection(innerBuilder);
			} else {
				attValue(innerBuilder);
			}

		}

		builder.add(new ObjectBodyContext(innerBuilder));

	}

	private void relSection(ObjectBodyContext.Builder builder) {
		consume(Type.RELATIONS);
		
		RelationSectionContext.Builder innerBuilder = new RelationSectionContext.Builder();
		
		//at least one
		relValue(innerBuilder);
		
		while (!match(Type.END)) {
			relValue(innerBuilder);
		}
		
		builder.add(new RelationSectionContext(innerBuilder));
		
		consume(Type.END);
	}

	private void attValue(ObjectBodyContext.Builder builder) {

		AttributeValueContext.Builder innerBuilder = new AttributeValueContext.Builder();

		innerBuilder.withAttributeIdentifier(currentToken.getValue());
		consume(Type.IDENTIFIER);
		consume(Type.COLON);
		innerBuilder.withValue(currentToken.getValue());
		consume(Type.STRING);

		builder.add(new AttributeValueContext(innerBuilder));
	}
	
	private void relValue(RelationSectionContext.Builder builder) {

		RelationValueContext.Builder innerBuilder = new RelationValueContext.Builder();

		innerBuilder.withRelationIdentifier(currentToken.getValue());
		consume(Type.IDENTIFIER);
		consume(Type.COLON);
		innerBuilder.withObjectIdentifier(currentToken.getValue());
		consume(Type.STRING);

		builder.addRelation(new RelationValueContext(innerBuilder));
	}

}
