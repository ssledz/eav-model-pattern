/*
 * Copyright 2013 Sławomir Śledź <slawomir.sledz@sof-tech.pl>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.softech.eav.domain.frame;

import java.util.Collection;
import java.util.Date;

import pl.softech.eav.domain.dictionary.DictionaryEntry;
import pl.softech.eav.domain.frame.Attribute;

/**
 * @author ssledz
 * @since 1.0
 */
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
