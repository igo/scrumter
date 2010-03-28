<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>



<h2><spring:message code="user.profile" /></h2>

<h3>${user.firstName} ${user.middleName} ${user.lastName}</h3>

<h4><spring:message code="user.profile.changePicture" /></h4>
<img src="<c:url value="/api/profile/picture?company=${user.company}&username=${user.username}"/>" style="float: left" />
<form action="<c:url value="/api/profile/picture"/>" method="post" enctype="multipart/form-data">
<input type="file" name="picture" /><br />
<input type="submit" value="<spring:message code="user.profile.changePicture" />" />
</form>
<br clear="left" /><br/>

<h4><spring:message code="user.profile.changePassword" /></h4>
<form action="<c:url value="/api/profile/change-password"/>" method="post">
<label for="password"><spring:message code="user.profile.newPassword" /></label><input type="password" name="password" id="password" /><br />
<label for="password2"><spring:message code="user.profile.repeatNewPassword" /></label><input type="password" name="password2" id="password2" />
<input type="submit" value="<spring:message code="user.profile.changePassword" />" />
</form>
