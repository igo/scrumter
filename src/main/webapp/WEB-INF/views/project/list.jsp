<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h1><spring:message code="projects" /></h1>
<c:forEach items="${groups}" var="group">
<div class="badge">
<h2>${group.name}</h2>
<span class="group-manager"><spring:message code="group.managedBy" /> <a href="<c:url value="/${group.author.company}/${group.author.username}/" />">${group.author.firstName} ${group.author.middleName} ${group.author.lastName}</a></span>
<br />
<c:forEach items="${group.members}" var="member" varStatus="status">
<a href="<c:url value="/${member.company}/${member.username}/" />">${member.firstName} ${member.middleName} ${member.lastName}</a><c:if test="${not status.last}">, </c:if>
</c:forEach>
</div>
</c:forEach>
