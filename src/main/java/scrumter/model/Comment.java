package scrumter.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Comment {

	@Id
	@GeneratedValue
	@Column
	private Long id;

	@ManyToOne
	private User author;

	@Column
	private String comment;

	@Column
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date created;
	
	@ManyToOne(optional = false, cascade = {CascadeType.ALL})
	public Status status;

	public Comment() {
		super();
	}

	public Comment(User author, String comment) {
		super();
		this.author = author;
		this.comment = comment;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", author=" + author + ", comment="
				+ comment + ", created=" + created + "]";
	}

}
