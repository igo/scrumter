<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h1><spring:message code="projects" /></h1>
<c:if test="${fn:length(groups) == 0}"><spring:message code="project.noProjects" /></c:if>
<c:forEach items="${groups}" var="group">
<div class="badge">
<h2><c:out value="${group.name}" /></h2>
<p><c:out value="${group.description}" /></p>
<spring:message code="managedBy" /> <a href="<c:url value="/${group.author.company}/${group.author.username}/" />">${group.author.firstName} ${group.author.middleName} ${group.author.lastName}</a> |
<a href="<c:url value="/projects/${group.link}/members/" />"><spring:message code="member.count" arguments="${groupsStats[group].members}" /></a> |
<a href="<c:url value="/projects/${group.link}/" />"><spring:message code="status.count" arguments="${groupsStats[group].statuses}" /></a>
</div>
</c:forEach>
