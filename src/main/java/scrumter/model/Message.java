package scrumter.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries(value = { @NamedQuery(name = "Message.findByFrom", query = "SELECT m FROM Message m ORDER BY m.created DESC"),
		@NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m ORDER BY m.created DESC"),
		@NamedQuery(name = "Message.findAllByAuthor", query = "SELECT m FROM Message m WHERE m.author = :author ORDER BY m.created DESC"),
		@NamedQuery(name = "Message.deleteAll", query = "DELETE FROM Message m") })
public class Message {

	@Id
	@GeneratedValue
	@Column
	private Long id;

	@ManyToOne
	private User author;

	@Column
	private String message;

	@Column
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date created;

	
	public Message() {
		super();
	}

	public Message(User author, String message) {
		super();
		this.author = author;
		this.message = message;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Message [id=");
		builder.append(id);
		builder.append(", author=");
		builder.append(author);
		builder.append(", message=");
		builder.append(message);
		builder.append(", created=");
		builder.append(created);
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}
