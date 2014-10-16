package pl.softech.eav.domain.dictionary;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import pl.softech.eav.domain.AbstractEntity;
import pl.softech.eav.domain.TextMedium;

/**
 * @author ssledz
 */
@Entity
@Table(name = "dictionary_entry")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DictionaryEntry extends AbstractEntity {

	@Valid
	@Embedded
	@AttributeOverride(name = DictionaryEntryIdentifier.IDENTIFIER_PROPERTY, column = @Column(nullable = false, unique = true))
	private DictionaryEntryIdentifier identifier;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dictionary_id", nullable = false)
	private Dictionary dictionary;

	@TextMedium
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private boolean disabled = false;

	protected DictionaryEntry() {
	}

	public DictionaryEntry(Dictionary dictionary, DictionaryEntryIdentifier identifier, String name) {
		this.dictionary = checkNotNull(dictionary, ARG_NOT_NULL_CHECK, "dictionary");
		this.identifier = checkNotNull(identifier, ARG_NOT_NULL_CHECK, "identifier");
		this.name = checkNotNull(name, ARG_NOT_NULL_CHECK, "Name");
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

	public String getName() {
		return name;
	}

	public boolean isEnabled() {
		return !disabled;
	}

	@Override
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
		sb.appendSuper(super.toString());
		sb.append(identifier);
		sb.append("name", name);
		return sb.toString();
	}
}
