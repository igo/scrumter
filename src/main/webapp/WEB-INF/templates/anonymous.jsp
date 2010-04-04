<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<title>Scrumter</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/css/screen.css" />" />
<script type="text/javascript" src="<c:url value="/js/lib/jquery-1.4.2.min.js" />"></script>

</head>
<body class="bp">
<div id="container">
<div id="header">
	<h1>Scrumter</h1>
	<hr />
</div>

<tiles:insertAttribute name="body" />

<div id="footer">
	<hr />
	&copy; Scrumter 2010
</div>

</div>

</body>
</html>
