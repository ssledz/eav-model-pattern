package pl.softech.learning.domain.eav.value;

public interface ValueWriter {
	String writeValue(AbstractValue<?> value);
}