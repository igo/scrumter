<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:useAttribute name="groups" ignore="false" />
<tiles:useAttribute name="projects" ignore="false" />
<ul>
	<li><a href="<c:url value="/" />"><spring:message code="news" /></a></li>
	<li><a href="<c:url value="/projects/" />"><spring:message code="projects" /></a>
		<ul>
			<c:forEach items="${projects}" var="group">
				<li><a href="<c:url value="/projects/${group.link}/" />"><c:out value="${group.name}" /></a></li>
			</c:forEach>
		</ul>
	</li>
	<li><a href="<c:url value="/groups/" />"><spring:message code="groups" /></a>
		<ul>
			<c:forEach items="${groups}" var="group">
				<li><a href="<c:url value="/groups/${group.link}/" />"><c:out value="${group.name}" /></a></li>
			</c:forEach>
		</ul>
	</li>
</ul>
