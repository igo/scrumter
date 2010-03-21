<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:useAttribute name="status" ignore="true" />

<div class="status" id="status-${status.id}">
	<a href=""><img src="<c:url value="/img/clovek1.jpg" />" class="profile-image" /></a>
	<div class="detail">
		<a href="<c:url value="/${status.author.company}/${status.author.username}/" />" class="name">${status.author.firstName} ${status.author.middleName} ${status.author.lastName}</a>
		${status.status}
		<br />
		
		<a href="/${status.author.company}/${status.author.username}/statuses/${status.id}" class="date" title="<fmt:formatDate value="${status.created}" type="date" pattern="dd MMM yyyy H:mm:ss Z"/>">${status.created}</a>
		<a href="#" class="buttonX" onclick="$('#write-comment-${status.id}').show();$('#write-comment-${status.id} textarea').focus();return false;"><spring:message code="status.comment" /></a>
		
		<div class="comments">
			<c:forEach var="comment" items="${status.comments}">
				<tiles:insertDefinition name="status/comment/detail">
					<tiles:putAttribute name="comment" value="${comment}"></tiles:putAttribute>
				</tiles:insertDefinition>
			</c:forEach>
		</div>
		<div class="write-comment <c:if test="${fn:length(status.comments) == 0}">hidden</c:if> minimizable minimized" id="write-comment-${status.id}">
			<form action="<c:url value="/api/status/add-comment" />" method="post" class="unchanged">
				<input type="hidden" name="statusId" value="${status.id}" />
				<textarea rows="2" cols="40" name="comment"><spring:message code="status.comment.write" /></textarea>
				<input type="submit" value="Comment" class="buttonXX" />
			</form>
		</div>
	</div>
</div>
