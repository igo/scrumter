package scrumter.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
	@Column
	private Long id;

	@NotNull
	@Length(min = 3, max = 24)
	@Column
	private String username;

	@NotNull
	@Length(min = 3, max = 24)
	@Column
	private String company;

	@NotNull
	@Pattern(regexp = ".+@.+\\..+")
	@Length(min = 5, max = 40)
	@Column
	private String email;

	@JsonIgnore
	@XmlTransient
	@NotNull
	@Column
	private String password;

	@NotNull
	@Length(min = 2, max = 24)
	@Column
	private String firstName;

	@Length(min = 2, max = 24)
	@Column
	private String middleName;

	@NotNull
	@Length(min = 2, max = 24)
	@Column
	private String lastName;

	@NotNull
	@Past
	@Column
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date created;

	@NotNull
	@Column
	private Boolean locked;
	
	@OneToMany
	private List<Authority> authorities = new ArrayList<Authority>();

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
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
		setCompany(email.substring(email.indexOf('@') + 1).toLowerCase());
		setUsername(email.substring(0, email.indexOf('@')).toLowerCase());
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
	
	public void grantRole(Authority authority) {
		authorities.add(authority);
	}
	
	public void revokeRole(Authority authority) {
		authorities.remove(authority);
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
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

}
