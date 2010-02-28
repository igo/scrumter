<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>


<div id="login">

<h1>Login</h1>

<form method="post" action="j_spring_security_check">
  	<fieldset>
  		<legend>Log in</legend>
		<p>	
			<label for="email" >E-mail</label><br/>
			<input type="text" name="j_username" />
		</p>
		<p>
			<label for="password">Password</label><br/>
			<input type="password" name="j_password" />
		</p>
		<p>	
			<input type="submit" value="Log in" />
		</p>
	</fieldset>
</form>

</div>

<div id="signup">
	<a href="<c:url value="/signup" />"><spring:message code="user.signup" text="Sign up" /></a>
</div>
