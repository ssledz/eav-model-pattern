package pl.softech.eav.domain.frame;

import java.util.Collection;

import pl.softech.eav.domain.frame.Relation;
import pl.softech.eav.domain.object.MyObject;

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
