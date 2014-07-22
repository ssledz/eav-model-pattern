package pl.softech.learning.domain.dictionary;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import pl.softech.learning.domain.AbstractEntity;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DictionaryEntry extends AbstractEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dictionary_id")
	private Dictionary dictionary;

	@Embedded
	@AttributeOverride(name = DictionaryEntryIdentifier.IDENTIFIER_PROPERTY, column = @Column(nullable = false, unique = true))
	private DictionaryEntryIdentifier identifier;

	private boolean disabled = false;

	protected DictionaryEntry() {
	}

	public DictionaryEntry(Dictionary dictionary, DictionaryEntryIdentifier identifier) {
		this.dictionary = checkNotNull(dictionary);
		this.identifier = checkNotNull(identifier);
	}

	public Dictionary getDictionary() {
		return dictionary;
	}

	public DictionaryEntryIdentifier getIdentifier() {
		return identifier;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public boolean isEnabled() {
		return !disabled;
	}

	@Override
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
		sb.appendSuper(super.toString());
		sb.append(identifier);
		return sb.toString();
	}
}
