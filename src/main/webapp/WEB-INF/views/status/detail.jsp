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
		<a href="#" class="buttonX" onclick="$('#write-comment-${status.id}').show();$('#write-comment-${status.id} textarea').focus();return false;">Comment</a>
		
		<div class="comments">
		<%--
			<div class="comment">
				<a href=""><img src="img/clovek_mini.jpg" class="profile-image-small" /></a>
				<a href="" class="name">Bla lba tasd</a>
				Cursus nibh convallis in id lacinia enim eleifend Lorem Cum Integer
				<br />
				<span class="date">Yesterday at 7:50pm</span>
				<br style="clear: both" />
			</div>
		 --%>
			
			
			<div class="write-comment write-comment-mini unchanged" id="write-comment-${status.id}">
				<form action="">
					<textarea rows="2" cols="50"
						onfocus="$('#write-comment-${status.id}').removeClass('write-comment-mini')"
						onblur="$('#write-comment-${status.id}.unchanged').addClass('write-comment-mini')"
						onchange="$('#write-comment-${status.id}').removeClass('unchanged')">Write comment...</textarea>
					<input type="submit" value="Comment" class="buttonXX" />
				</form>
			</div>
		</div>
	</div>
</div>
