<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:useAttribute name="projectGroups" ignore="false" />
<tiles:useAttribute name="publicGroups" ignore="false" />
<tiles:useAttribute name="currentUser" ignore="false" />
<div id="groups">
	<div class="group">
		<a href="<c:url value="/${currentUser.company}/${currentUser.username}/projects/" />" class="group-link">
			<span class="group-count">${projectGroups}</span><br />
			<spring:message code="projects" />
		</a>
	</div>
	<div class="group">
		<a href="<c:url value="/${currentUser.company}/${currentUser.username}/groups/" />" class="group-link">
			<span class="group-count">${publicGroups}</span><br />
			<spring:message code="groups" />
		</a>
	</div>
</div>