<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:useAttribute name="notification" ignore="false" />

<div class="notification badge" id="notification-${notification.id}">
<c:choose>
<c:when test="${notification.type == 'comment'}">
<a href="<c:url value="/${notification.user.company}/${notification.user.username}/" />" class="name">${notification.user.firstName} ${notification.user.middleName} ${notification.user.lastName}</a>
<spring:message code="notification.commentedOn" /> <a href="<c:url value="/${notification.user.company}/${notification.user.username}/statuses/${notification.status.id}/" />" class="name"><spring:message code="notification.yourStatus" /></a>
</c:when>
<c:otherwise>Unknown notifications.</c:otherwise>
</c:choose>
</div>
