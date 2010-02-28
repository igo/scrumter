<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form modelAttribute="signupForm" method="post" action="signup">
  	<fieldset>
  		<legend>Sign up</legend>
		<p>
			<form:label	for="firstName" path="firstName" cssErrorClass="error">First name</form:label><br/>
			<form:input path="firstName" /> <form:errors path="firstName" />	
		</p>
		<p>
			<form:label	for="lastName" path="lastName" cssErrorClass="error">Last name</form:label><br/>
			<form:input path="lastName" /> <form:errors path="lastName" />			
		</p>
		<p>	
			<form:label for="email" path="email" cssErrorClass="error">E-mail</form:label><br/>
			<form:input path="email" /> <form:errors path="email" />
		</p>
		<p>
			<form:label for="password" path="password" cssErrorClass="error">Password</form:label><br/>
			<form:password path="password" /> <form:errors path="password" />
		</p>
		<p>	
			<input type="submit" value="Sign up" />
		</p>
	</fieldset>
</form:form>

