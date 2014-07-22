package pl.softech.learning.domain.eav;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import pl.softech.learning.domain.AbstractEntity;
import pl.softech.learning.domain.dictionary.DictionaryEntry;

@Entity
public class Value extends AbstractEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	private DomainObject object;

	@ManyToOne(fetch = FetchType.LAZY)
	private Attribute attribute;

	@Column(name = "string_value")
	private String stringValue;

	@Column(name = "boolean_value")
	private Boolean booleanValue;

	@Column(name = "integer_value")
	private Integer integerValue;

	@Column(name = "double_value")
	private Double doubleValue;

	@Column(name = "dict_value")
	@ManyToOne(fetch = FetchType.LAZY)
	private DictionaryEntry dictionaryValue;

}
