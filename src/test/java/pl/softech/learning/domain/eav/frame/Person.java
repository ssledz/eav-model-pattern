package pl.softech.learning.domain.eav.frame;

import java.util.Collection;

import pl.softech.learning.domain.eav.MyObject;

public interface Person {

	String getFirstname();
	
	void setFirstname(String firstname);
	
	String getLastname();
	
	void setLastname(String lastname);
	
	int getAge();
	
	void setAge(int age);
	
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
