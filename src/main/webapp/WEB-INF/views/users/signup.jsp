<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>


<div id="signup-content">

<div id="signup">
	<h2><spring:message code="user.account.signup" /></h2>
	<form:form modelAttribute="signupForm" method="post" action="signup">
	  	<fieldset>
			<p>
				<form:label	for="firstName" path="firstName" cssErrorClass="error"><spring:message code="user.firstName" /></form:label><br/>
				<form:input path="firstName" /> <form:errors path="firstName" />	
			</p>
			<p>
				<form:label	for="lastName" path="lastName" cssErrorClass="error"><spring:message code="user.lastName" /></form:label><br/>
				<form:input path="lastName" /> <form:errors path="lastName" />			
			</p>
			<p>	
				<form:label for="email" path="email" cssErrorClass="error"><spring:message code="user.email" /></form:label><br/>
				<form:input path="email" /> <form:errors path="email" />
			</p>
			<p>
				<form:label for="password" path="password" cssErrorClass="error"><spring:message code="user.password" /></form:label><br/>
				<form:password path="password" /> <form:errors path="password" />
			</p>
			<p>	
				<input type="submit" value="<spring:message code="user.account.signup" />" />
			</p>
		</fieldset>
	</form:form>
</div>

<div id="login">
	<h2><spring:message code="user.account.login" /></h2>
	<spring:message code="user.account.have" />
	<a href="<c:url value="/" />"><spring:message code="user.account.login" /></a>
</div>

</div>
