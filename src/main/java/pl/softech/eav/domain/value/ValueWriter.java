package pl.softech.eav.domain.value;

/**
 * @author ssledz
 * @since 1.0
 */
public interface ValueWriter {
	
	String writeValue(AbstractValue<?> value);
	
}