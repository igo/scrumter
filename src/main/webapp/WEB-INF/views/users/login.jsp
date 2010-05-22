<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>

<div id="login-content">

<div id="login">
	<h2><spring:message code="user.account.login" /></h2>
	<c:if test="${not empty param.login_error}">
		<p class="error"><spring:message code="user.account.login.incorrectLoginOrPassword" /></p>
	</c:if>

	<form method="post" action="j_spring_security_check">
	  	<fieldset>
			<p>	
				<label for="email"><spring:message code="user.email" /></label><br/>
				<input type="text" name="j_username" id="email" />
			</p>
			<p>
				<label for="password"><spring:message code="user.password" /></label><br/>
				<input type="password" name="j_password" id="password" />
			</p>
			<p>
				<input type="checkbox" name="_spring_security_remember_me" id="remember-me">
				<label for="remember-me"><spring:message code="user.account.login.rememberMe" /></label>
			</p>
			<p>	
				<input type="submit" value="<spring:message code="user.account.login" />" />
			</p>
		</fieldset>
	</form>
</div>

<div id="signup">
	<h2><spring:message code="user.account.signup" /></h2>
	<spring:message code="user.account.dontHave" />
	<a href="<c:url value="/signup" />"><spring:message code="user.account.signup" /></a>
</div>

</div>
