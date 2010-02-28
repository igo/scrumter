<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="posts">
<c:choose>
	<c:when test="${fn:length(messages) > 0}">
		<c:forEach var="message" items="${messages}">
			<tiles:insertDefinition name="messages/detail">
				<tiles:putAttribute name="message" value="${message}"></tiles:putAttribute>
			</tiles:insertDefinition>
		</c:forEach>
	</c:when>
	<c:otherwise>
		No messages yet.
	</c:otherwise>
</c:choose>
</div>

<script>
$(document).ready(function(){

});
</script>

