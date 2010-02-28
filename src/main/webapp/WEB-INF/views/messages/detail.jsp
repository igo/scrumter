<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:useAttribute name="message" ignore="true" />

<div class="message" id="message-${message.id}">
	<a href=""><img src="<c:url value="/img/clovek1.jpg" />" class="profile-image" /></a>
	<div class="detail">
		<a href="<c:url value="/${message.author.company}/${message.author.username}/" />" class="name">${message.author.firstName} ${message.author.middleName} ${message.author.lastName}</a>
		${message.message}
		<br />
		<a href="/${message.author.company}/${message.author.username}/messages/${message.id}" class="date" title="2010-01-22T21:14:17Z">${message.created}</a>
		<a href="#" class="buttonX" onclick="$('#write-comment-${message.id}').show();$('#write-comment-${message.id} textarea').focus();return false;">Comment</a>
		
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
			
			
			<div class="write-comment write-comment-mini unchanged" id="write-comment-${message.id}">
				<form action="">
					<textarea rows="2" cols="50"
						onfocus="$('#write-comment-${message.id}').removeClass('write-comment-mini')"
						onblur="$('#write-comment-${message.id}.unchanged').addClass('write-comment-mini')"
						onchange="$('#write-comment-${message.id}').removeClass('unchanged')">Write comment...</textarea>
					<input type="submit" value="Comment" class="buttonXX" />
				</form>
			</div>
		</div>
	</div>
</div>
