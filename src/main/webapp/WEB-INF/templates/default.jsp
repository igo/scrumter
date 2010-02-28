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

<link rel="stylesheet" href="http://jqueryui.com/themes/base/jquery-ui.css" type="text/css" media="all" />
<link rel="stylesheet" href="http://static.jquery.com/ui/css/demo-docs-theme/ui.theme.css" type="text/css" media="all" />

<script type="text/javascript" src="<c:url value="/js/jquery-1.3.2.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/jquery-ui-1.7.2.custom.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/fcbkcomplete/jquery.fcbkcomplete.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/prettydate.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/pure.js" />"></script>

<script type="text/javascript">

function doPrettyDates() {
	$("a").prettyDate();
}

$(document).ready(function(){
	//setInterval(function(){ doPrettyDates(); }, 1000);
$("#allowed").fcbkcomplete({
    json_url: "js/fcbkcomplete/fetched.txt",
    cache: true,
    filter_case: false,
    filter_hide: true,
	firstselected: true,
    //onremove: "testme",
	//onselect: "testme",
    filter_selected: true,
    newel: false
});
	$("#message-form").submit(function(){
		$.post('<c:url value="/api/messages/add" />', $("#message-form").serialize(), function(data) {
			var post = $(data).hide().prependTo('.posts');
			doPrettyDates();
			post.slideDown("slow");
		});
		return false;
	});
});
</script>

</head>
<body>
<div id="container">
<div id="header">
	<h1>Scrumter</h1>
	<a href="<c:url value="/" />"><spring:message code="menu.home" text="Home" /></a> |
	<a href="<c:url value="/users" />"><spring:message code="menu.users" text="Users" /></a> |
	<a href="<c:url value="/profile" />"><spring:message code="menu.profile" text="Profile" /></a> |
	<a href="<c:url value="/signup" />"><spring:message code="user.signup" text="Sign up" /></a> |
	<a href="<c:url value="/login" />"><spring:message code="security.login" text="Login" /></a> |
	<a href="<c:url value="/logout" />"><spring:message code="security.logout" text="Logout" /></a> |
	<hr />
	<div>
	<c:choose>
		<c:when test="${pageContext.request.userPrincipal != null}">
		 	<span>${pageContext.request.userPrincipal.principal.username}</span> |
			<span>			    
				<spring:url var="logout" value="/logout" />
				<a href="${logout}"><spring:message code="security.logout" text="Logout" /></a>
			</span>
		</c:when>
		<c:otherwise>
			<span>anonymous</span> |
			<span>			    
				<spring:url var="login" value="/login" />
				<a href="${login}"><spring:message code="security.login" text="Login" /></a>
			</span>
		</c:otherwise>
	</c:choose>	
	</div>
</div>


<hr />

<div id="content"><tiles:insertAttribute name="body" /></div>

<div id="sidebar">
Sidebar
<h2>Users</h2>

</div>


<div id="footer">Footer<hr />
${pageContext.request.userPrincipal}
</div>

</div>

</body>
</html>
