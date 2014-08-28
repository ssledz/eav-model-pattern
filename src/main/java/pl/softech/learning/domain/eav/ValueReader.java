package pl.softech.learning.domain.eav;

public interface ValueReader {
	AbstractValue<?> readValue(String value);
}