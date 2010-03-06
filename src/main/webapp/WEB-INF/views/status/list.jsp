<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="posts">
<c:choose>
	<c:when test="${fn:length(statuses) > 0}">
		<c:forEach var="status" items="${statuses}">
			<tiles:insertDefinition name="status/detail">
				<tiles:putAttribute name="status" value="${status}"></tiles:putAttribute>
			</tiles:insertDefinition>
		</c:forEach>
	</c:when>
	<c:otherwise>
		No statuses yet.
	</c:otherwise>
</c:choose>
</div>

<script>
$(document).ready(function(){

});
</script>

