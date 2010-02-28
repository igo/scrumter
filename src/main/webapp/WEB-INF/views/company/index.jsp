<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<script type="text/javascript">
	$(function() {
		$("div.tabs").tabs();
	});
</script>


<h1>${company}</h1>

<div class="tabs">
	<ul>
		<li><a href="#posts"><spring:message code="posts" text="Posts" /></a></li>
		<li><a href="#members"><spring:message code="members" text="Members" /></a></li>
	</ul>
	<div id="posts">
		<p>Proin elit arcu, rutrum commodo, vehicula tempus, commodo a, risus. Curabitur nec arcu. Donec sollicitudin mi sit amet mauris. Nam elementum quam ullamcorper ante. Etiam aliquet massa et lorem. Mauris dapibus lacus auctor risus. Aenean tempor ullamcorper leo. Vivamus sed magna quis ligula eleifend adipiscing. Duis orci. Aliquam sodales tortor vitae ipsum. Aliquam nulla. Duis aliquam molestie erat. Ut et mauris vel pede varius sollicitudin. Sed ut dolor nec orci tincidunt interdum. Phasellus ipsum. Nunc tristique tempus lectus.</p>
	</div>
	<div id="members">
		<p>
			<c:choose>
				<c:when test="${fn:length(users) > 0}">
					<c:forEach var="user" items="${users}">
					<div>
					<a href="<c:url value="/${user.company}/${user.username}/" />">${user.firstName} ${user.middleName} ${user.lastName}</a>
					 - 
					<a href="<c:url value="/${user.company}/${user.username}/follow" />">Follow</a>
					</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					No users found.
				</c:otherwise>
			</c:choose>	
		</p>
	</div>
</div>





