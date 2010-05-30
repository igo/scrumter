package scrumter.model.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Length;

@Entity
@NamedQueries(value = { @NamedQuery(name = "User.findAllExcept", query = "SELECT u FROM User u WHERE u <> :user"),
		@NamedQuery(name = "User.findByFrom", query = "SELECT m FROM User m"),
		@NamedQuery(name = "User.findByCompany", query = "SELECT u FROM User u WHERE u.company = :company"),
		@NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
		@NamedQuery(name = "User.findByUsernameAndCompany", query = "SELECT u FROM User u WHERE u.username = :username AND u.company = :company"),
		@NamedQuery(name = "User.deleteAll", query = "DELETE FROM User u") })
public class User {

	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Long id;

	@NotNull
	@Length(min = 3, max = 24)
	@Column(nullable = false)
	private String username;

	@NotNull
	@Length(min = 3, max = 24)
	@Column(nullable = false)
	private String company;

	@NotNull
	@Pattern(regexp = ".+@.+\\..+")
	@Length(min = 5, max = 40)
	@Column(nullable = false)
	private String email;

	@JsonIgnore
	@XmlTransient
	@NotNull
	@Column(nullable = false)
	private String password;

	@NotNull
	@Length(min = 2, max = 24)
	@Column(nullable = false)
	private String firstName;

	@Length(min = 2, max = 24)
	@Column
	private String middleName;

	@NotNull
	@Length(min = 2, max = 24)
	@Column(nullable = false)
	private String lastName;

	@Column
	private String position;

	@NotNull
	@Past
	@Column(nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date created;

	@NotNull
	@Column(nullable = false)
	private Boolean locked = false;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] picture;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] picturePreview;

	@NotNull
	@Column(nullable = false)
	private Boolean emailGroupMembershipChange = true;

	@NotNull
	@Column(nullable = false)
	private Boolean emailStatusInGroup = true;

	@NotNull
	@Column(nullable = false)
	private Boolean emailCommentOnGroupStatus = true;

	@NotNull
	@Column(nullable = false)
	private Boolean emailCommentOnOwnStatus = true;

	@ManyToMany
	private Set<Authority> authorities = new HashSet<Authority>();

	@ManyToMany
	@OrderBy("name")
	private Set<Group> membership = new HashSet<Group>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getEmail() {
		return email;
	}

	/**
	 * Set user's email address. This will also change user's username and company
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
		setCompany(email.substring(email.indexOf('@') + 1).toLowerCase());
		setUsername(email.substring(0, email.indexOf('@')).toLowerCase());
	}

	public void addMembership(Group group) {
		membership.add(group);
	}

	@JsonIgnore
	@XmlTransient
	public String getPassword() {
		return password;
	}

	@JsonIgnore
	@XmlTransient
	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public byte[] getPicturePreview() {
		return picturePreview;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPosition() {
		return position;
	}

	public void grantRole(Authority authority) {
		authorities.add(authority);
	}

	public void revokeRole(Authority authority) {
		authorities.remove(authority);
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public Set<Group> getMembership() {
		return membership;
	}

	public void setMembership(Set<Group> membership) {
		this.membership = membership;
	}

	public Boolean getEmailGroupMembershipChange() {
		return emailGroupMembershipChange;
	}

	public void setEmailGroupMembershipChange(Boolean emailGroupMembershipChange) {
		this.emailGroupMembershipChange = emailGroupMembershipChange;
	}

	public Boolean getEmailStatusInGroup() {
		return emailStatusInGroup;
	}

	public void setEmailStatusInGroup(Boolean emailStatusInGroup) {
		this.emailStatusInGroup = emailStatusInGroup;
	}

	public Boolean getEmailCommentOnGroupStatus() {
		return emailCommentOnGroupStatus;
	}

	public void setEmailCommentOnGroupStatus(Boolean emailCommentOnGroupStatus) {
		this.emailCommentOnGroupStatus = emailCommentOnGroupStatus;
	}

	public Boolean getEmailCommentOnOwnStatus() {
		return emailCommentOnOwnStatus;
	}

	public void setEmailCommentOnOwnStatus(Boolean emailCommentOnOwnStatus) {
		this.emailCommentOnOwnStatus = emailCommentOnOwnStatus;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=");
		builder.append(id);
		builder.append(", username=");
		builder.append(username);
		builder.append(", locked=");
		builder.append(locked);
		builder.append(", created=");
		builder.append(created);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
