package scrumter.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CollectionOfElements;

@Entity
@NamedQueries(value = {
		@NamedQuery(name = "Status.findByFrom", query = "SELECT s FROM Status s ORDER BY s.created DESC"),
		@NamedQuery(name = "Status.findAll", query = "SELECT s FROM Status s ORDER BY s.created DESC"),
		@NamedQuery(name = "Status.findAllByAuthor", query = "SELECT s FROM Status s WHERE s.author = :author ORDER BY s.created DESC"),
		@NamedQuery(name = "Status.deleteAll", query = "DELETE FROM Status s") })
public class Status {

	@Id
	@GeneratedValue
	@Column
	private Long id;

	@ManyToOne
	private User author;

	@Column
	private String status;

	@Column
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date created;

	@OneToMany(mappedBy = "status", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	@OrderBy("created")
	private List<Comment> comments = new ArrayList<Comment>();

	public Status() {
		super();
	}

	public Status(User author, String status) {
		super();
		this.author = author;
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Status [id=");
		builder.append(id);
		builder.append(", author=");
		builder.append(author);
		builder.append(", status=");
		builder.append(status);
		builder.append(", created=");
		builder.append(created);
		builder.append(", comments=");
		builder.append(comments);
		builder.append("]");
		return builder.toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}
