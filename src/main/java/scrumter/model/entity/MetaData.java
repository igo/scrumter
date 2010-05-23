package scrumter.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MetaData {

	@Id
	@GeneratedValue
	@Column
	private Long id;

	@Column(name = "k")
	private String key;

	@Column(name = "v")
	private String value;

	public MetaData() {
		super();
	}

	public MetaData(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
