<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:useAttribute name="comment" ignore="true" />

<div class="comment" id="comment-${comment.id}">
	<a href="<c:url value="/${comment.author.company}/${comment.author.username}/" />"><img src="<c:url value="/api/profile/picture?company=${comment.author.company}&username=${comment.author.username}" />" class="profile-picture-small" /></a>
	<a href="<c:url value="/${comment.author.company}/${comment.author.username}/" />" class="name">${comment.author.firstName} ${comment.author.middleName} ${comment.author.lastName}</a>
	<c:out value="${comment.comment}" />
	<br />
	<span class="date" title="<fmt:formatDate value="${comment.created}" type="date" pattern="dd MMM yyyy H:mm:ss Z"/>">${comment.created}</span>
	<br style="clear: both" />
</div>
