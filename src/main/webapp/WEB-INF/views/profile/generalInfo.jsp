<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h1><spring:message code="menu.profile.basicInformation" /></h1>

<form action="<c:url value="/api/profile/change-password"/>" method="post">
<h2>${user.firstName} ${user.middleName} ${user.lastName}</h2>
<p>
<spring:message code="from" />: <a href="<c:url value="/${user.company}/" />">${user.company}</a><br />
<spring:message code="user.email" />: <a href="mailto:${user.email}">${user.email}</a><br />
<spring:message code="user.position" />: ${user.position}<br />
</p>
</form>
