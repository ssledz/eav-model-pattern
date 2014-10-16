package pl.softech.eav.domain.value;

/**
 * @author ssledz
 */
public interface ValueWriter {
	
	String writeValue(AbstractValue<?> value);
	
}