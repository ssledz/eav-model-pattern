package pl.softech.eav.domain.value;

public interface ValueWriter {
	String writeValue(AbstractValue<?> value);
}