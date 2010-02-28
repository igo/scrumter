<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<title>Scrumter</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/css/screen.css" />" />
<link type="text/css" rel="stylesheet" href="<c:url value="/js/fcbkcomplete/style.css" />" />
<script type="text/javascript" src="<c:url value="/js/jquery-1.3.2.min.js" />"></script>

</head>
<body>
<div id="container">
<div id="header">
	<h1>Scrumter</h1>
</div>

<div id="content"><tiles:insertAttribute name="body" /></div>

<div id="footer">Footer<hr />
	<ul>
		<li> <a href="?locale=en_us">us</a> |  <a href="?locale=en_gb">gb</a> | <a href="?locale=es_es">es</a> | <a href="?locale=de_de">de</a> </li>
	</ul>	
&copy; Scrumter 2010
</div>

</div>

</body>
</html>
