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

import pl.softech.eav.domain.frame.Relation;
import pl.softech.eav.domain.object.MyObject;

/**
 * @author ssledz
 * @since 1.0
 */
public interface Person {

	String getFirstname();
	
	void setFirstname(String firstname);
	
	String getLastname();
	
	void setLastname(String lastname);
	
	Integer getAge();
	
	void setAge(Integer age);
	
	@Relation(name="has_computer")
	Computer getComputer();
	
	void setComputer(Computer computer);
	
	@Relation(name="has_friend")
	Collection<Person> getFriends();
	
	@Relation(name="has_friend")
	void addFriend(Person friend);
	
	MyObject getParent();
	
	@Relation(name="has_parent")
	void setParent(MyObject parent);
	
}
