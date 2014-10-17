package pl.softech.eav.domain.value;

/**
 * @author ssledz
 * @since 1.0 
 */
public interface ValueReader {
	AbstractValue<?> readValue(String value);
}