<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<ul>
	<li><a href="<c:url value="/profile/" />"><spring:message code="menu.profile.basicInformation" /></a></li>
	<li><a href="<c:url value="/profile/profile-picture/" />"><spring:message code="menu.profile.profilePicture" /></a></li>
	<li><a href="<c:url value="/profile/email-notifications/" />"><spring:message code="menu.profile.emailNotifications" /></a></li>
	<li><a href="<c:url value="/profile/change-password/" />"><spring:message code="menu.profile.changePassword" /></a></li>
</ul>
