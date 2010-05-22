<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h1>${company}</h1>
<c:forEach items="${members}" var="member">
<div class="user badge">
	<a href="<c:url value="/${member.company}/${member.username}/" />"><img src="<c:url value="/api/profile/picture?company=${member.company}&username=${member.username}" />" class="profile-picture" /></a>
	<div class="detail">
		<a href="<c:url value="/${member.company}/${member.username}/" />" class="name"><c:out value="${member.firstName} ${member.middleName} ${member.lastName}" /></a>
		<spring:message code="from" /> <a href="<c:url value="/${member.company}/" />"><c:out value="${member.company}" /></a>
		<p><c:out value="${member.position}" /></p>
		<a href="mailto:${member.email}"><spring:message code="sendEmail" /></a>
	</div>
</div>
</c:forEach>
