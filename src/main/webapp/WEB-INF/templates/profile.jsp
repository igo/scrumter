<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:useAttribute name="body" />

<div id="navbar"><tiles:insertDefinition name="profile/menu" /></div>

<div id="content" class="wide"><tiles:insertAttribute name="body" /></div>
