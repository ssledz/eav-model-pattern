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

import static com.google.common.base.Preconditions.checkState;

import java.util.Collection;
import java.util.Map;

import pl.softech.eav.domain.attribute.Attribute;
import pl.softech.eav.domain.category.Category;
import pl.softech.eav.domain.object.MyObject;
import pl.softech.eav.domain.relation.RelationConfiguration;

import com.google.common.collect.Maps;

/**
 * @author ssledz
 * @since 1.1 
 */
public class DefaultSymbolTable implements SymbolTable {

	private Map<String, Category> id2cat = Maps.newHashMap();
	private Map<String, Attribute> id2att = Maps.newHashMap();
	private Map<String, MyObject> id2obj = Maps.newHashMap();
	private Map<String, RelationConfiguration> id2rel = Maps.newHashMap();

	@Override
	public void put(String identifier, Category cat) {
		checkState(id2cat.put(identifier, cat) == null, "Category %s already exists ", cat);
	}

	@Override
	public void put(String identifier, Attribute att) {
		checkState(id2att.put(identifier, att) == null, "Attribute %s already exists ", att);
	}

	@Override
	public void put(String identifier, MyObject obj) {
		checkState(id2obj.put(identifier, obj) == null, "MyObject %s already exists ", obj);
	}

	@Override
	public void put(String identifier, RelationConfiguration rel) {
		checkState(id2rel.put(identifier, rel) == null, "Relation %s already exists ", rel);
	}

	@Override
	public Category getCategory(String identifier) {
		return id2cat.get(identifier);
	}

	@Override
	public Attribute getAttribute(String identifier) {
		return id2att.get(identifier);
	}

	@Override
	public MyObject getObject(String identifier) {
		return id2obj.get(identifier);
	}

	@Override
	public RelationConfiguration getRelation(String identifier) {
		return id2rel.get(identifier);
	}
	
	@Override
	public Collection<Category> getCategories() {
		return id2cat.values();
	}

	@Override
	public Collection<Attribute> getAttributes() {
		return id2att.values();
	}

	@Override
	public Collection<RelationConfiguration> getRelations() {
		return id2rel.values();
	}

	@Override
	public Collection<MyObject> getObjects() {
		return id2obj.values();
	}

}