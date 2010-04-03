<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<tiles:useAttribute name="groups" ignore="true" />
<div class="minimized minimizable">
<form action="<c:url value="/statuses/add" />" id="status-form" class="unchanged">
<textarea name="status" rows="4" cols="55"><spring:message code="status.form.title" /></textarea><br />
<select id="allowedXXX" name="allowed">
	<c:forEach var="group" items="${groups}">
		<option value="${group.id}">${group.name}</option>
	</c:forEach>
</select>
<input type="submit" value="<spring:message code="status.form.share" />" />
</form>
</div>
