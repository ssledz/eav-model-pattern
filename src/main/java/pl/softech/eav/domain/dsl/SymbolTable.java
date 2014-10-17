package pl.softech.eav.domain.dsl;

import java.util.Collection;

import pl.softech.eav.domain.attribute.Attribute;
import pl.softech.eav.domain.category.Category;
import pl.softech.eav.domain.object.MyObject;
import pl.softech.eav.domain.relation.RelationConfiguration;

/**
 * @author ssledz
 * @since 1.1 
 */
public interface SymbolTable {
	
	void put(String identifier, Category cat);

	void put(String identifier, Attribute att);

	void put(String identifier, MyObject obj);

	void put(String identifier, RelationConfiguration rel);

	Category getCategory(String identifier);

	Attribute getAttribute(String identifier);

	MyObject getObject(String identifier);

	RelationConfiguration getRelation(String identifier);

	Collection<MyObject> getObjects();

	Collection<RelationConfiguration> getRelations();

	Collection<Attribute> getAttributes();

	Collection<Category> getCategories();
}
