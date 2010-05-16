<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h1><spring:message code="menu.profile.changePassword" /></h1>

<form action="<c:url value="/api/profile/change-password"/>" method="post">
<label for="password"><spring:message code="user.profile.newPassword" /></label><input type="password" name="password" id="password" /><br />
<label for="password2"><spring:message code="user.profile.repeatNewPassword" /></label><input type="password" name="password2" id="password2" />
<input type="submit" value="<spring:message code="user.profile.changePassword" />" />
</form>
