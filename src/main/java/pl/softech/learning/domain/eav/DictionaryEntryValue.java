package pl.softech.learning.domain.eav;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import pl.softech.learning.domain.dictionary.DictionaryEntry;

@Embeddable
public class DictionaryEntryValue extends AbstractValue<DictionaryEntry>{

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dictionary_entry_id")
	private DictionaryEntry value;

	protected DictionaryEntryValue() {
	}

	public DictionaryEntryValue(DictionaryEntry value) {
		this.value = checkNotNull(value);
	}

	@Override
	public DictionaryEntry getValue() {
		return value;
	}

	@Override
	public void accept(ValueVisitor visitor) {
		visitor.visit(this);
	}
	
	
	
}
