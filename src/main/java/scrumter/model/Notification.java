package scrumter.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries(value = {
		@NamedQuery(name = "Notification.findAllByOwner", query = "SELECT n FROM Notification n WHERE n.owner = :owner ORDER BY n.created"),
		@NamedQuery(name = "Notification.delete", query = "DELETE FROM Notification n WHERE n = :notification"),
		@NamedQuery(name = "Notification.deleteAllByOwner", query = "DELETE FROM Notification n WHERE n.owner = :owner") })
public class Notification {

	@Id
	@GeneratedValue
	@Column
	private Long id;

	@ManyToOne
	private User owner;

	@NotNull
	@Column
	private String type;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column
	private Date created;

	@OneToMany
	private Set<MetaData> meta = new HashSet<MetaData>();

	public Notification() {
		super();
	}

	public Notification(String type, User user) {
		super();
		this.type = type;
		this.owner = user;
	}

	public Notification(String type, User user, Set<MetaData> meta) {
		super();
		this.type = type;
		this.owner = user;
		this.meta = meta;
	}

	public void addMetaData(MetaData metaData) {
		meta.add(metaData);
	}

	public void addMeta(String key, String value) {
		addMetaData(new MetaData(key, value));
	}

	public Set<MetaData> getMeta() {
		return meta;
	}

	public void setMeta(Set<MetaData> meta) {
		this.meta = meta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User user) {
		this.owner = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}
