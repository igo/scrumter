package scrumter.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "groups")
@NamedQueries(value = {
		@NamedQuery(name = "Group.findAll", query = "SELECT g FROM Group g"),
		@NamedQuery(name = "Group.deleteAll", query = "DELETE FROM Group g") })
public class Group {

	@Id
	@GeneratedValue
	@Column
	private Long id;

	@NotNull
	@Length(min = 3, max = 40)
	@Column
	private String name;

	@ManyToOne
	private User author;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column
	private Date created;

	@ManyToMany(mappedBy = "membership")
	private Set<User> members;

	public Group() {
		super();
		this.created = new Date();
		this.members = new HashSet<User>();
	}

	public Group(String name, User author) {
		super();
		this.name = name;
		this.author = author;
		this.created = new Date();
		this.members = new HashSet<User>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Date getCreated() {
		return created;
	}

	public Set<User> getMembers() {
		return members;
	}

	public void setMembers(Set<User> members) {
		this.members = members;
	}

	public void addMember(User user) {
		members.add(user);
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", members=" + members
				+ "]";
	}

}
