<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h1><spring:message code="menu.profile.profilePicture" /></h1>

<img src="<c:url value="/api/profile/picture?company=${user.company}&username=${user.username}"/>" style="float: left" />
<form action="<c:url value="/api/profile/picture"/>" method="post" enctype="multipart/form-data">
<input type="file" name="picture" /><br />
<input type="submit" value="<spring:message code="user.profile.changePicture" />" />
</form>
