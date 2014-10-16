package pl.softech.eav.domain.value;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import pl.softech.eav.domain.dictionary.DictionaryEntry;

/**
 * @author ssledz
 */
@Embeddable
public class DictionaryEntryValue extends AbstractValue<DictionaryEntry>{

	@ManyToOne(fetch = FetchType.LAZY)
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
	
	@Override
	public String asString() {
		return getValue() != null ? getValue().getName() : "null";
	}
	
}
