package pl.softech.learning.domain.eav;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.tuple.Pair;

import pl.softech.learning.domain.AbstractEntity;
import pl.softech.learning.domain.eav.category.Category;
import pl.softech.learning.domain.eav.relation.Relation;
import pl.softech.learning.domain.eav.relation.RelationConfiguration;
import pl.softech.learning.domain.eav.relation.RelationIdentifier;
import pl.softech.learning.domain.eav.specification.ObjectMatchAttributeSpecification;
import pl.softech.learning.domain.eav.specification.ValueMatchAttributeSpecification;
import pl.softech.learning.domain.eav.value.AbstractValue;
import pl.softech.learning.domain.eav.value.BooleanValue;
import pl.softech.learning.domain.eav.value.DateValue;
import pl.softech.learning.domain.eav.value.DictionaryEntryValue;
import pl.softech.learning.domain.eav.value.DoubleValue;
import pl.softech.learning.domain.eav.value.IntegerValue;
import pl.softech.learning.domain.eav.value.ObjectValue;
import pl.softech.learning.domain.eav.value.StringValue;
import pl.softech.learning.domain.eav.value.ValueVisitor;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author ssledz
 */
@Entity
public class MyObject extends AbstractEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	private Category category;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "object")
	private Set<ObjectValue> values = Sets.newHashSet();

	private String name;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "owner")
	private Set<Relation> relations = Sets.newHashSet();

	protected MyObject() {
	}

	public MyObject(Builder builder) {
		this(builder.category, builder.name);

		for (Pair<Attribute, ? extends AbstractValue<?>> p : builder.values) {
			addValue(p.getLeft(), p.getRight());
		}

	}

	public MyObject(Category category, String name) {
		this.category = checkNotNull(category);
		this.name = checkNotNull(name);
	}

	public Category getCategory() {
		return category;
	}

	public ImmutableSet<ObjectValue> getValues() {
		return new ImmutableSet.Builder<ObjectValue>().addAll(values).build();
	}

	public ImmutableSet<ObjectValue> getValuesByAttribute(final AttributeIdentifier attributeIdentifier) {
		return new ImmutableSet.Builder<ObjectValue>().addAll(Iterables.filter(values, new Predicate<ObjectValue>() {

			@Override
			public boolean apply(ObjectValue input) {
				return input.getAttribute().getIdentifier().equals(attributeIdentifier);
			}
		})).build();
	}

	public ObjectValue getValueByAttribute(AttributeIdentifier attributeIdentifier) {
		return Iterables.getOnlyElement(getValuesByAttribute(attributeIdentifier), null);
	}

	public boolean hasValues(AttributeIdentifier attributeIdentifier) {
		return !getValuesByAttribute(attributeIdentifier).isEmpty();
	}

	public ImmutableSet<Relation> getRelations() {
		return new ImmutableSet.Builder<Relation>().addAll(relations).build();
	}

	public ImmutableSet<Relation> getRelationsByIdentifier(final RelationIdentifier identifier) {

		return new ImmutableSet.Builder<Relation>().addAll(Iterables.filter(relations, new Predicate<Relation>() {

			@Override
			public boolean apply(Relation input) {
				return input.getConfigurarion().getIdentifier().equals(identifier);
			}
		})).build();

	}

	public Relation getRelationByIdentifier(RelationIdentifier identifier) {
		return Iterables.getOnlyElement(getRelationsByIdentifier(identifier), null);
	}

	public Relation addRelation(RelationConfiguration configurarion, MyObject target) {
		Relation r = new Relation(configurarion, this, target);
		relations.add(r);
		return r;
	}

	public <T extends AbstractValue<?>> ObjectValue updateValue(final Attribute attribute, T value) {

		checkNotNull(attribute);

		ObjectValue objValue = getValueByAttribute(attribute.getIdentifier());

		if (objValue != null) {
			values.remove(objValue);
		}

		if (value == null) {
			return null;
		}

		return addValue(attribute, value);
	}

	public <T extends AbstractValue<?>> ObjectValue addValue(final Attribute attribute, T value) {

		checkNotNull(attribute);
		checkNotNull(value);

		ObjectMatchAttributeSpecification catConstraint = new ObjectMatchAttributeSpecification();

		checkArgument(catConstraint.isSafisfiedBy(Pair.of(this, attribute)), String.format(
				"Object category %s doesn't match the Attribute category %s", category.getIdentifier().getIdentifier(), attribute
						.getCategory().getIdentifier().getIdentifier()));

		ValueMatchAttributeSpecification dataTypeConstraint = new ValueMatchAttributeSpecification();

		checkArgument(dataTypeConstraint.isSafisfiedBy(Pair.of(value, attribute)),
				String.format("Attribute %s doesn't match the value %s", attribute.toString(), value.toString()));

		final ObjectValue[] bag = new ObjectValue[1];

		ValueVisitor visitor = new ValueVisitor() {

			@Override
			public void visit(DateValue value) {
				bag[0] = new ObjectValue(attribute, MyObject.this, value);
			}

			@Override
			public void visit(DictionaryEntryValue value) {
				bag[0] = new ObjectValue(attribute, MyObject.this, value);
			}

			@Override
			public void visit(DoubleValue value) {
				bag[0] = new ObjectValue(attribute, MyObject.this, value);
			}

			@Override
			public void visit(IntegerValue value) {
				bag[0] = new ObjectValue(attribute, MyObject.this, value);
			}

			@Override
			public void visit(BooleanValue value) {
				bag[0] = new ObjectValue(attribute, MyObject.this, value);
			}

			@Override
			public void visit(StringValue value) {
				bag[0] = new ObjectValue(attribute, MyObject.this, value);
			}
		};

		value.accept(visitor);

		checkNotNull(bag[0]);

		values.add(bag[0]);

		return bag[0];
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
		sb.appendSuper(super.toString());
		sb.append("name", name);
		sb.append("category", category);
		sb.append("values", values);
		return sb.toString();
	}

	public static class Builder {

		private Category category;

		private String name;

		private Collection<Pair<Attribute, ? extends AbstractValue<?>>> values = Lists.newLinkedList();

		public Builder withCategory(Category category) {
			this.category = category;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public <T extends AbstractValue<?>> Builder add(Attribute attribute, T value) {
			values.add(Pair.of(attribute, value));
			return this;
		}

	}

}
