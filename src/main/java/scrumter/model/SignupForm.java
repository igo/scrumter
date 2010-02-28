package scrumter.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class SignupForm {
	
	@NotNull
	@Size(min = 2, max = 20)
	private String firstName;

	@NotNull
	@Size(min = 2, max = 20)
	private String lastName;

	@NotNull
	@Pattern(regexp = ".+@.+\\..+")
	@Size(min = 2, max = 40)
	private String email;

	@NotNull
	@Size(min = 8)
	private String password;
	
	public User getUser() {
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPassword(password);
		user.setEmail(email);
		return user;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
