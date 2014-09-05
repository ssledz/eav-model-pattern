package pl.softech.learning.domain.eav.frame;

import java.util.Collection;
import java.util.Date;

import pl.softech.learning.domain.dictionary.DictionaryEntry;

public interface Computer {

	DictionaryEntry getMake();
	
	String getModel();
	
	void setModel(String model);
	
	DictionaryEntry getType();
	
	String getCpu();
	
	String getDrive();
	
	String getVideo();
	
	void setVideo(String video);
	
	Integer getRam();
	
	String getOptical();
	
	String getBattery();
	
	String getScreen();
	
	Collection<DictionaryEntry> getOs();
	
	void addOs(DictionaryEntry od);
	
	@Attribute(name="purshase_date")
	Date getPurshaseDate();
	
	@Attribute(name="for_sale")
	Boolean isForSale();
	
	@Attribute(name="for_sale")
	void setForSale(Boolean forSale);
	
}
