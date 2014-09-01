package pl.softech.learning.domain.eav.value;

public interface ValueReader {
	AbstractValue<?> readValue(String value);
}