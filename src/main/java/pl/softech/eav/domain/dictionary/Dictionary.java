package pl.softech.eav.domain.dictionary;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.FetchMode;

import pl.softech.eav.domain.AbstractEntity;
import pl.softech.eav.domain.TextMedium;

/**
 * @author ssledz
 */
@Entity
@Table(name = "dictionary")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Dictionary extends AbstractEntity {

	@Valid
	@Embedded
	@AttributeOverride(name = DictionaryIdentifier.IDENTIFIER_PROPERTY, column = @Column(nullable = false, unique = true))
	private DictionaryIdentifier identifier;

	@TextMedium
	@Column(nullable = false)
	private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dictionary", cascade = CascadeType.ALL, orphanRemoval = true)
	@org.hibernate.annotations.Fetch(FetchMode.SUBSELECT)
	@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<DictionaryEntry> entries = new ArrayList<DictionaryEntry>();

	protected Dictionary() {
	}

	public Dictionary(DictionaryIdentifier identifier, String name) {
		this.identifier = checkNotNull(identifier, ARG_NOT_NULL_CHECK, "Identifier");
		this.name = checkNotNull(name, ARG_NOT_NULL_CHECK, "Name");
	}

	public List<DictionaryEntry> getEntries() {
		return Collections.unmodifiableList(entries);
	}

	public String getName() {
		return name;
	}

	public DictionaryIdentifier getIdentifier() {
		return identifier;
	}

	public List<DictionaryEntry> getEnabledEntries() {
		List<DictionaryEntry> enabledEntries = new ArrayList<DictionaryEntry>();
		for (DictionaryEntry entry : entries) {
			if (!entry.isDisabled()) {
				enabledEntries.add(entry);
			}
		}
		return Collections.unmodifiableList(enabledEntries);
	}

	@Override
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
		sb.appendSuper(super.toString());
		sb.append(identifier);
		return sb.toString();
	}
}
