package pl.softech.learning.domain.eav.frame;

import java.util.Collection;
import java.util.Date;

import pl.softech.learning.domain.eav.value.DictionaryEntryValue;

public interface Computer {

	DictionaryEntryValue getMake();
	
	String getModel();
	
	DictionaryEntryValue getType();
	
	String getCpu();
	
	String getDrive();
	
	String getVideo();
	
	Integer getRam();
	
	String getOptical();
	
	String getBattery();
	
	String getScreen();
	
	Collection<DictionaryEntryValue> getOs();
	
	@Attribute(name="purshase_date")
	Date getOurshaseDate();
	
}
