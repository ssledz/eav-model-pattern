package pl.softech.learning.domain.eav.dsl;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.Collection;
import java.util.Map;

import pl.softech.learning.domain.dictionary.Dictionary;
import pl.softech.learning.domain.dictionary.DictionaryIdentifier;
import pl.softech.learning.domain.dictionary.DictionaryRepository;
import pl.softech.learning.domain.eav.Attribute;
import pl.softech.learning.domain.eav.DataType;
import pl.softech.learning.domain.eav.DataTypeSerialisationService;
import pl.softech.learning.domain.eav.MyObject;
import pl.softech.learning.domain.eav.category.Category;
import pl.softech.learning.domain.eav.value.AbstractValue;

import com.google.common.collect.Maps;

/**
 * @author ssledz
 */
public class CreateModelVisitor implements ContextVisitor {

	private static class SymbolTable {

		private Map<String, Category> id2cat = Maps.newHashMap();
		private Map<String, Attribute> id2att = Maps.newHashMap();
		private Map<String, MyObject> id2obj = Maps.newHashMap();

		public void put(String identifier, Category cat) {
			checkState(id2cat.put(identifier, cat) == null, "Category %s already exists ", cat);
		}

		public void put(String identifier, Attribute att) {
			checkState(id2att.put(identifier, att) == null, "Attribute %s already exists ", att);
		}

		public void put(String identifier, MyObject obj) {
			checkState(id2obj.put(identifier, obj) == null, "MyObject %s already exists ", obj);
		}

		public Category getCategory(String identifier) {
			return id2cat.get(identifier);
		}

		public Attribute getAttribute(String identifier) {
			return id2att.get(identifier);
		}

		@SuppressWarnings("unused")
		public MyObject getObject(String identifier) {
			return id2obj.get(identifier);
		}

	}

	private final SymbolTable symbolTable = new SymbolTable();

	private Category.Builder currentCategorBuilder;

	private Attribute.Builder currentAttributeBuilder;

	private MyObject.Builder currentObjectBuilder;

	private final DictionaryRepository dictionaryRepository;
	
	private final DataTypeSerialisationService dataTypeSerialisationService;

	public CreateModelVisitor(DictionaryRepository dictionaryRepository, DataTypeSerialisationService dataTypeSerialisationService) {
		this.dictionaryRepository = dictionaryRepository;
		this.dataTypeSerialisationService = dataTypeSerialisationService;
	}

	public Collection<Category> getCategories() {
		return symbolTable.id2cat.values();
	}

	public Collection<Attribute> getAttributes() {
		return symbolTable.id2att.values();
	}

	public Collection<MyObject> getObjects() {
		return symbolTable.id2obj.values();
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

		Attribute attribute = checkNotNull(symbolTable.getAttribute(ctx.getAttributeIdentifier()), "No attribute with identifier %s", ctx.getAttributeIdentifier());
		AbstractValue<?> value = dataTypeSerialisationService.readValue(attribute.getDataType().getType(), ctx.getValue());

		currentObjectBuilder.add(attribute, value);

	}

	@Override
	public void visitOnLeave(ObjectBodyContext ctx) {
	}

	@Override
	public void visitOnEnter(RelationDefinitionContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitOnLeave(RelationDefinitionContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OwnerPropertyContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TargetPropertyContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(RelationValueContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitOnEnter(RelationSectionContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitOnLeave(RelationSectionContext ctx) {
		// TODO Auto-generated method stub
		
	}

}
