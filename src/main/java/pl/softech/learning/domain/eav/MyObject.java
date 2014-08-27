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

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Entity
public class MyObject extends AbstractEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	private Category category;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "object")
	private Set<ObjectValue> values = Sets.newHashSet();

	private String name;

	protected MyObject() {
	}

	public MyObject(Builder builder) {
		this(builder.category, builder.name);
		
		for(Pair<Attribute, ? extends AbstractValue<?>> p : builder.values) {
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
		return Iterables.getOnlyElement(getValuesByAttribute(attributeIdentifier));
	}

	public boolean hasValues(AttributeIdentifier attributeIdentifier) {
		return !getValuesByAttribute(attributeIdentifier).isEmpty();
	}

	public <T extends AbstractValue<?>> ObjectValue addValue(final Attribute attribute, T value) {

		checkNotNull(attribute);
		checkNotNull(value);

		ObjectCategoryMatchAttributeConstraint catConstraint = new ObjectCategoryMatchAttributeConstraint();

		checkArgument(catConstraint.isSafisfiedBy(Pair.of(this, attribute)), String.format(
				"Object category %s doesn't match the Attribute category %s", category.getIdentifier().getIdentifier(), attribute
						.getCategory().getIdentifier().getIdentifier()));

		ValueMatchAttributeConstraint dataTypeConstraint = new ValueMatchAttributeConstraint();

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
		
		public  <T extends AbstractValue<?>> Builder add(Attribute attribute, T value) {
			values.add(Pair.of(attribute, value));
			return this;
		}
		

	}

}
