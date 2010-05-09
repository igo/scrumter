<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:useAttribute name="notification" ignore="false" />
<tiles:useAttribute name="currentUser" ignore="false" />

<div class="notification badge" id="notification-${notification.id}">
<c:choose>
	<c:when test="${notification.type == 'comment'}">
		<c:set var="who"><a href="<c:url value="/${notification.user.company}/${notification.user.username}/" />" class="name">${notification.user.firstName} ${notification.user.middleName} ${notification.user.lastName}</a></c:set>
		<c:set var="whose"><a href="<c:url value="/${notification.status.author.company}/${notification.status.author.username}/" />" class="name">${notification.status.author.firstName} ${notification.status.author.middleName} ${notification.status.author.lastName}</a></c:set>
		<c:set var="status"><a href="<c:url value="/${notification.status.author.company}/${notification.status.author.username}/statuses/${notification.status.id}/" />" class="name"><spring:message code="notification.comment.status" /></a></c:set>
		
		<c:choose>
			<c:when test="${currentUser.id == notification.status.author.id}">
				<spring:message code="notification.comment.onYour" arguments="${who},${whose},${status}" />
			</c:when>
			<c:when test="${notification.user.id == notification.status.author.id}">
				<spring:message code="notification.comment.onHis" arguments="${who},${whose},${status}" />
			</c:when>
			<c:otherwise>
				<spring:message code="notification.comment.on" arguments="${who},${whose},${status}" />
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>Unknown notifications.</c:otherwise>
</c:choose>
</div>
