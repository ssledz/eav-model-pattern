package pl.softech.eav.domain.value;

public interface ValueReader {
	AbstractValue<?> readValue(String value);
}