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
<body>
<div id="header">
	<a href="<c:url value="/" />"><img src="<c:url value="/img/logo.png" />" /></a>
</div>
<div id="container">
<tiles:insertAttribute name="body" />
</div>

<div id="footer">Powered by Scrumter | Knowledge is its own reward</div>

</body>
</html>
