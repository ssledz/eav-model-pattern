package pl.softech.learning.domain.eav;

public interface ValueWriter {
	String writeValue(AbstractValue<?> value);
}