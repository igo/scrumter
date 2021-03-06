<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:useAttribute name="body" />

<div id="navbar"><tiles:insertDefinition name="menu/home" /></div>

<div id="content"><tiles:insertAttribute name="body" /></div>

<div id="sidebar">
	<tiles:insertDefinition name="notification/list" />
</div>
