<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<div class="minimized minimizable">
<form action="<c:url value="/statuses/add" />" id="status-form" class="unchanged">
<textarea name="status" rows="4" cols="70"><spring:message code="status.form.title" /></textarea><br />
<%--<select id="allowed" name="allowed"></select> --%>

<input type="submit" value="<spring:message code="status.form.share" />" />
</form>
</div>
