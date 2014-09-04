package pl.softech.learning.domain.eav.frame;

import java.util.Collection;
import java.util.Date;

import pl.softech.learning.domain.dictionary.DictionaryEntry;

public interface Computer {

	DictionaryEntry getMake();
	
	String getModel();
	
	DictionaryEntry getType();
	
	String getCpu();
	
	String getDrive();
	
	String getVideo();
	
	Integer getRam();
	
	String getOptical();
	
	String getBattery();
	
	String getScreen();
	
	Collection<DictionaryEntry> getOs();
	
	@Attribute(name="purshase_date")
	Date getPurshaseDate();
	
	@Attribute(name="for_sale")
	Boolean isForSale();
	
}
