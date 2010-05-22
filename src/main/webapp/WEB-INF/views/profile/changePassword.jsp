<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h1><spring:message code="menu.profile.changePassword" /></h1>

<form action="<c:url value="/api/profile/change-password"/>" method="post" id="form-change-password">
<p>
	<label for="current-password"><spring:message code="user.profile.currentPassword" /></label><br />
	<input type="password" name="currentPassword" id="current-password" class=":required" /><br />
</p>
<p>
	<label for="new-password"><spring:message code="user.profile.newPassword" /></label><br />
	<input type="password" name="newPassword" id="new-password" class=":required" /><br />
</p>
<p>
	<label for="password2"><spring:message code="user.profile.repeatNewPassword" /></label><br />
	<input type="password" name="newPassword2" id="password2" class=":same_as;new-password" /><br />
</p>
<p>
	<input type="submit" value="<spring:message code="user.profile.changePassword" />" />
</p>
</form>

<script type="text/javascript">

$(document).ready(function() {
$("#form-change-password").submit(function() {
	var form = this;
	$.post(form.action, $(form).serialize(), function(data, result, request) {
		$("<div/>").html(data.response.message).dialog({
			title: '<spring:message code="user.profile.changePassword" javaScriptEscape="true" htmlEscape="false" />',
			modal: true,
			buttons: {
				OK: function() {
					$(this).dialog('close');
				}
			}
		});
		if (data.response.success) {
			form.reset();
		}
	});
	return false;
});
});
</script>