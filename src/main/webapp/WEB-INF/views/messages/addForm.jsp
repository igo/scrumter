<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h2><spring:message code="status.form.title" /></h2>
<form action="<c:url value="/messages/add" />" id="status-form">
<textarea name="message" rows="4" cols="70"></textarea><br />
<%--<select id="allowed" name="allowed"></select> --%>

<input type="submit" value="<spring:message code="status.form.share" />" />
</form>
