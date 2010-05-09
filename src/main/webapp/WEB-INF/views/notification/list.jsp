<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:useAttribute name="notifications" ignore="false" />
<tiles:useAttribute name="currentUser" ignore="false" />

<h2><spring:message code="notifications" /></h2>
<div class="notifications">
<c:choose>
	<c:when test="${fn:length(notifications) > 0}">
		<c:forEach var="notification" items="${notifications}">
			<tiles:insertDefinition name="notification/detail">
				<tiles:putAttribute name="notification" value="${notification}" />
				<tiles:putAttribute name="currentUser" value="${currentUser}" />
			</tiles:insertDefinition>
		</c:forEach>
	</c:when>
	<c:otherwise><spring:message code="notification.noNotifications" /></c:otherwise>
</c:choose>
</div>
