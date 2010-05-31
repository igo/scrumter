<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h1><spring:message code="menu.profile.emailNotifications" /></h1>

<form action="<c:url value="/api/profile/email-notifications"/>" method="post" id="form-change-email-notifications">
<p>
	<input id="membership-change" type="checkbox" name="emailGroupMembershipChange" <c:if test="${user.emailGroupMembershipChange}">checked="checked"</c:if> />
	<label for="membership-change"><spring:message code="user.profile.emailNotifications.emailGroupMembershipChange" /></label><br />
</p>
<p>
	<input id="email-status" type="checkbox" name="emailStatus" <c:if test="${user.emailStatus}">checked="checked"</c:if> />
	<label for="email-status"><spring:message code="user.profile.emailNotifications.emailStatus" /></label><br />
</p>
<p>
	<input id="email-comment-status" type="checkbox" name="emailCommentOnStatus" <c:if test="${user.emailCommentOnStatus}">checked="checked"</c:if> />
	<label for="email-comment-status"><spring:message code="user.profile.emailNotifications.emailCommentOnStatus" /></label><br />
</p>
<p>
	<input id="email-comment-own-status" type="checkbox" name="emailCommentOnOwnStatus" <c:if test="${user.emailCommentOnOwnStatus}">checked="checked"</c:if> />
	<label for="email-comment-own-status"><spring:message code="user.profile.emailNotifications.emailCommentOnOwnStatus" /></label><br />
</p>
<p>
	<input id="email-comment-touched-status" type="checkbox" name="emailCommentOnTouchedStatus" <c:if test="${user.emailCommentOnTouchedStatus}">checked="checked"</c:if> />
	<label for="email-comment-touched-status"><spring:message code="user.profile.emailNotifications.emailCommentOnTouchedStatus" /></label><br />
</p>

<p>
	<input type="submit" value="<spring:message code="save" />" />
</p>
</form>

<script type="text/javascript">

$(document).ready(function() {
$("#form-change-email-notifications").submit(function() {
	var form = this;
	$.post(form.action, $(form).serialize(), function(data, result, request) {
		$("<div/>").html(data.message).dialog({
			title: '<spring:message code="menu.profile.emailNotifications" javaScriptEscape="true" htmlEscape="false" />',
			modal: true,
			buttons: {
				OK: function() {
					$(this).dialog('close');
				}
			}
		});
	});
	return false;
});
});
</script>