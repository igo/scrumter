<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<tiles:useAttribute name="groups" ignore="true" />
<tiles:useAttribute name="projects" ignore="true" />
<div id="status-form-wrapper">
<form action="<c:url value="/statuses/add" />" id="status-form" class="unchanged">
<label for="status-form-textarea"><spring:message code="status.form.title" /></label>
<textarea id="status-form-textarea" name="status" rows="1" cols="55"></textarea>
<div id="status-form-tools">
	<div class="allowed-wrapper">
	<select id="allowed" name="allowedGroups" multiple="multiple">
		<optgroup label="<spring:message code="projects" />">
			<c:forEach var="groupItem" items="${projects}">
				<option value="${groupItem.id}"<c:if test="${groupItem.id == group.id}"> selected="selected"</c:if>><c:out value="${groupItem.name}" /></option>
			</c:forEach>
		</optgroup>
		<optgroup label="<spring:message code="groups" />">
			<c:forEach var="groupItem" items="${groups}">
				<option value="${groupItem.id}"<c:if test="${groupItem.id == group.id}"> selected="selected"</c:if>><c:out value="${groupItem.name}" /></option>
			</c:forEach>
		</optgroup>
	</select>
	</div>
	<div class="submit-wrapper">
		<input type="submit" value="<spring:message code="status.form.share" />" />
	</div>
</div>
</form>
</div>
