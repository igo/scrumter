<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script>
    function callSigninUser(){
        console.log($('#loginForm'));
        $.post("/scrumter/statuses/data.json", $("#loginForm").serialize());
    }
</script>


<h2><spring:message code="user.users" /></h2>
<c:choose>
	<c:when test="${fn:length(users) > 0}">
		<c:forEach var="user" items="${users}">
		<div>
		<a href="<c:url value="/users/${user.username}" />">${user.firstName} ${user.middleName} ${user.lastName}</a>
		 - 
		<a href="<c:url value="/users/${user.username}/follow" />">Follow</a>
		</div>
		</c:forEach>
	</c:when>
	<c:otherwise>
		No users found.
	</c:otherwise>
</c:choose>	

