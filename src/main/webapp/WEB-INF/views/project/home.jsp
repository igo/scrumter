<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h1><spring:message code="project.home.title" arguments="${group.name}" htmlEscape="true" /></h1>

<tiles:insertDefinition name="status/addForm" />

<tiles:insertDefinition name="status/list" />
