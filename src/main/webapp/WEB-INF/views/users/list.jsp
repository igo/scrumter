<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script>
    function callSigninUser(){
        console.log($('#loginForm'));
        $.post("/scrumter/messages/data.json", $("#loginForm").serialize());
    }
</script>



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

