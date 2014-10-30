Entity-Attribute-Value model
===============

Entity–attribute–value model (EAV) is a data model to describe entities where the number of attributes (properties, parameters) that can be used to describe them is potentially vast, but the number that will actually apply to a given entity is relatively modest. In mathematics, this model is known as a sparse matrix. EAV is also known as object–attribute–value model, vertical database model and open schema.

##EAV database schema
Data is recorded in the following tables
* **eav_attribute**
* **eav_category**
* **eav_dictionary**
* **eav_dictionary_entry**
* **eav_my_object**
* **eav_object_value**
* **eav_rel_configuration**
* **eav_relation**

###eav_category
Stores all defined categories referenced by
* **eav_attribute**
* **eav_my_object**
* **eav_rel_configuration**

It plays a role of glue between objects, attributes and relations. Attributes and relations related to the one object have the same category.

Properties:
* **id** - unique
* **identifier** - unique 
* **name**

Example of categories
* person
* computer

```
+------------+--------------+------+-----+---------+----------------+
| Field      | Type         | Null | Key | Default | Extra          |
+------------+--------------+------+-----+---------+----------------+
| id         | bigint(20)   | NO   | PRI | NULL    | auto_increment |
| identifier | varchar(255) | NO   | UNI | NULL    |                |
| name       | varchar(255) | NO   |     | NULL    |                |
+------------+--------------+------+-----+---------+----------------+
```

###eav_attribute

```
+---------------+--------------+------+-----+---------+----------------+
| Field         | Type         | Null | Key | Default | Extra          |
+---------------+--------------+------+-----+---------+----------------+
| id            | bigint(20)   | NO   | PRI | NULL    | auto_increment |
| data_type     | varchar(255) | NO   |     | NULL    |                |
| identifier    | varchar(255) | NO   | UNI | NULL    |                |
| name          | varchar(255) | NO   |     | NULL    |                |
| category_id   | bigint(20)   | NO   | MUL | NULL    |                |
| dictionary_id | bigint(20)   | YES  | MUL | NULL    |                |
+---------------+--------------+------+-----+---------+----------------+
```

###eav_rel_configuration

```
+---------------+--------------+------+-----+---------+----------------+
| Field         | Type         | Null | Key | Default | Extra          |
+---------------+--------------+------+-----+---------+----------------+
| id            | bigint(20)   | NO   | PRI | NULL    | auto_increment |
| identifier    | varchar(255) | NO   | UNI | NULL    |                |
| name          | varchar(255) | NO   |     | NULL    |                |
| owner_cat_id  | bigint(20)   | NO   | MUL | NULL    |                |
| target_cat_id | bigint(20)   | NO   | MUL | NULL    |                |
+---------------+--------------+------+-----+---------+----------------+
```

###eav_my_object

```
+-------------+--------------+------+-----+---------+----------------+
| Field       | Type         | Null | Key | Default | Extra          |
+-------------+--------------+------+-----+---------+----------------+
| id          | bigint(20)   | NO   | PRI | NULL    | auto_increment |
| name        | varchar(255) | NO   |     | NULL    |                |
| category_id | bigint(20)   | NO   | MUL | NULL    |                |
+-------------+--------------+------+-----+---------+----------------+
```

###eav_object_value

```
+---------------------+--------------+------+-----+---------+----------------+
| Field               | Type         | Null | Key | Default | Extra          |
+---------------------+--------------+------+-----+---------+----------------+
| id                  | bigint(20)   | NO   | PRI | NULL    | auto_increment |
| boolean_value       | bit(1)       | YES  |     | NULL    |                |
| date_value          | date         | YES  |     | NULL    |                |
| double_value        | double       | YES  |     | NULL    |                |
| integer_value       | int(11)      | YES  |     | NULL    |                |
| string_value        | varchar(255) | YES  |     | NULL    |                |
| attribute_id        | bigint(20)   | NO   | MUL | NULL    |                |
| dictionary_entry_id | bigint(20)   | YES  | MUL | NULL    |                |
| object_id           | bigint(20)   | NO   | MUL | NULL    |                |
+---------------------+--------------+------+-----+---------+----------------+
```

###eav_relation

```
+------------+------------+------+-----+---------+----------------+
| Field      | Type       | Null | Key | Default | Extra          |
+------------+------------+------+-----+---------+----------------+
| id         | bigint(20) | NO   | PRI | NULL    | auto_increment |
| rel_cnf_id | bigint(20) | NO   | MUL | NULL    |                |
| owner_id   | bigint(20) | NO   | MUL | NULL    |                |
| target_id  | bigint(20) | YES  | MUL | NULL    |                |
+------------+------------+------+-----+---------+----------------+
```

###eav_dictionary

```
+------------+--------------+------+-----+---------+----------------+
| Field      | Type         | Null | Key | Default | Extra          |
+------------+--------------+------+-----+---------+----------------+
| id         | bigint(20)   | NO   | PRI | NULL    | auto_increment |
| identifier | varchar(255) | NO   | UNI | NULL    |                |
| name       | varchar(255) | NO   |     | NULL    |                |
+------------+--------------+------+-----+---------+----------------+
```

###eav_dictionary_entry

```
+---------------+--------------+------+-----+---------+----------------+
| Field         | Type         | Null | Key | Default | Extra          |
+---------------+--------------+------+-----+---------+----------------+
| id            | bigint(20)   | NO   | PRI | NULL    | auto_increment |
| disabled      | bit(1)       | NO   |     | NULL    |                |
| identifier    | varchar(255) | NO   | UNI | NULL    |                |
| name          | varchar(255) | NO   |     | NULL    |                |
| dictionary_id | bigint(20)   | NO   | MUL | NULL    |                |
+---------------+--------------+------+-----+---------+----------------+
```

##EAV Api

Logically api is organized into three main modules 
* **frame api**
 * *pl.softech.eav.domain.frame*
* **dsl api**
 * *pl.softech.eav.domain.dsl*
* model **manipulation/query api** being stored in the following packages
 * *pl.softech.eav.domain.attribute*
 * *pl.softech.eav.domain.category*
 * *pl.softech.eav.domain.dictionary*
 * *pl.softech.eav.domain.object*
 * *pl.softech.eav.domain.relation*
 * *pl.softech.eav.domain.specification*
 * *pl.softech.eav.domain.calue*

###Frame Api
It is an abstraction which works well in case when we have well defined entity model. Each object of class `MyObject` can be framed with an interface.
Thanks to this manipulation and querying tasks on `MyObject` can be performed on the interface simply calling appropriate methods.
 
**Examples**
```java
public interface Computer {

	DictionaryEntry getMake();
	
	String getModel();
	
	void setModel(String model);
}
```

```java
MyObject object = new MyObject(
	categoryRepository.findByIdentifier("computer"), 
	"MAUI"
);
Computer computer = frameFactory.frame(Computer.class, object);
computer.setModel("Studio15");
```

###Model manipulation/query api

**Examples**

Create a computer object

```java
MyObject computer = new MyObject(
	categoryRepository.findByIdentifier("computer"), 
	"STAR"
);
```

Create a person object

```java
MyObject person = new MyObject(
	categoryRepository.findByIdentifier("person"), 
	"Slavik"
);
```

Add dictionary value to the computer object

```java
computer.addValue(
	attributeRepository.findByIdentifier(new AttributeIdentifier("make")), 
	new DictionaryEntryValue(dictionaryEntryRepository.findByIdentifier("dell"))
);
```

Add string value to the computer object

```java
computer.addValue(
	attributeRepository.findByIdentifier(new AttributeIdentifier("model")), 
	new StringValue("Studio15")
);
```

Configure 'has computer' relation between person and computer

```java
RelationConfiguration hasComputer = new RelationConfiguration(
	new RelationIdentifier("has_computer"), 
	"has",
	categoryRepository.findByIdentifier("person"), 
	categoryRepository.findByIdentifier("computer")
);
```

Add 'has computer' relation to the person object
```java
person.addRelation(hasComputer, computer);
```

Ask for a value for the given attribute as a string
```java
ObjectValue makeValue = obj.getValueByAttribute(new AttributeIdentifier("make"));
String make = makeValue.getValueAsString();
```

Ask for a value for given attribute, assuming that the value is of DictionaryEntry type
```java
ObjectValue makeValue = obj.getValueByAttribute(new AttributeIdentifier("make"));
final DictionaryEntryValue[] bag = new DictionaryEntryValue[1];
makeValue.accept(new ValueVisitorAdapter() {
	@Override
	public void visit(DictionaryEntryValue value) {
		bag[0] = value;
	}
});
```

Ask for a value for a given attribue, assuming that the value is of Integer type
```java
ObjectValue ramValue = obj.getValueByAttribute(new AttributeIdentifier("ram"));
final Integer[] bag = new Integer[1];
ramValue.accept(new ValueVisitorAdapter() {
	@Override
	public void visit(IntegerValue value) {
		bag[0] = value;
	}
});
```

Ask for a collection of values for a given attribute
```java
Set<ObjectValue> oss = obj.getValuesByAttribute(new AttributeIdentifier("os"));
```

Ask for a given relation
```java
Relation relation = person.getRelationByIdentifier(
	new RelationIdentifier("has_computer")
);
```

###Dsl api

Pros
* TODO

Cons
* TODO

##EAV Dsl parser

Characteristics
* hand written
 * top-down parsing strategy
* clear separation from domain model (`CreateModelVisitor`)
 * utilized visitor and context object pattern

```java
CreateModelVisitor visitor = new CreateModelVisitor(
	dictionaryRepository, 
	dataTypeSerialisationService
);
Parser p = new Parser(visitor);
p.parse(buffer.toString());
```

##EAV Domain specific language

Pros
* easy to learn, clear structure
* fast domain model preparation
* easy to maintenance
* ideal for the domain experts
* supports comments

Cons
* there is no IDE support for syntax highlighting
* there is no IDE support for syntax autocomplete

Resources
* [EBNF](https://github.com/ssledz/eav-model-pattern/blob/master/eav-core/src/conf/eav-dsl.ebnf)
* [Computer-Person example](https://github.com/ssledz/eav-model-pattern/blob/master/eav-examples/eav-computer-person-example/src/main/resources/computer-person.eav)


##Todo
- [ ] dsl parser
  - [ ] add line number to the parse error
  - [ ] try to print as many errors as possible
  - [ ] add possibility to reference objects not yet defined
- [ ] examples
  - [ ] add 'Curriculum Vitae' example 
  - [ ] add relation and objects creating on the fly example
- [ ] documentation
  - [ ] document computer-person example
  - [ ] javadoc ?
- [ ] core
  - [ ] introduce new json object value
  - [ ] enhance eav entities with audit capability
  - [ ] introduce liquibase migrations
    - [ ] mysql
    - [ ] hsql
    - [ ] oracle
  - [ ] test
    - [ ] implement tests marked with //TODO implement
- [ ] add google analytics tracking scripts
- [ ] setup continuous integration (maybe should try http://www.cloudbees.com/)

##Resources
* http://en.wikipedia.org/wiki/Entity-attribute-value_model
* http://stackoverflow.com/questions/1336449/eav-over-sql-server/1336471#1336471
* http://eav.codeplex.com/
* http://stackoverflow.com/questions/126271/key-value-pairs-in-relational-database
* http://stackoverflow.com/questions/6661841/the-concept-of-implementing-key-value-stores-with-relational-database-languages

##Problems
* [Submin an issue](https://github.com/ssledz/eav-model-pattern/issues)

##License
   Copyright 2013 Sławomir Śledź <slawomir.sledz@sof-tech.pl>.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
   
        http://www.apache.org/licenses/LICENSE-2.0
   
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
