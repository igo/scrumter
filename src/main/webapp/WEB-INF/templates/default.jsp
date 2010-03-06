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
scrumter = {
	strings: {
		statusFormTitle: '<spring:message code="status.form.title" />'
	}
}


function doPrettyDates() {
	$("a.date").prettyDate();
}

$(document).ready(function(){
	doPrettyDates();
	setInterval(function(){ doPrettyDates(); }, 10000);
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
	$("#status-form").submit(function(){
		$.post('<c:url value="/api/status/add" />', $("#status-form").serialize(), function(data) {
			var post = $(data).hide().prependTo('.posts');
			doPrettyDates();
			post.slideDown("slow");
			$("#status-form").addClass("minimized unchanged");
			$("#status-form textarea").val(scrumter.strings.statusFormTitle);
		});
		return false;
	});

	$(".minimizable textarea").each(function() {
		$(this).focus(function() {
			$(this).parent(".minimizable").removeClass("minimized");
			$(this).parent(".minimizable.unchanged").find("textarea").val('');
		});
		$(this).blur(function() {
			$(this).parent(".minimizable.unchanged").addClass("minimized");
			$(this).parent(".minimizable.unchanged").find("textarea").val(scrumter.strings.statusFormTitle);
		});
		$(this).change(function() {
			$(this).parent(".minimizable").removeClass("unchanged");
		});
	});
	
});
</script>

</head>
<body class="bp">
<div id="container">
<div id="header">
	<h1><a href="<c:url value="/" />">Scrumter</a></h1>
	<div id="main-menu">
		<c:choose>
			<c:when test="${pageContext.request.userPrincipal != null}">
			 	<span>${pageContext.request.userPrincipal.principal.username}</span> |
			</c:when>
			<c:otherwise>
				<span>anonymous</span> |
				<span>			    
					<a href="<c:url value="/login" />"><spring:message code="user.account.login" /></a>
				</span>
			</c:otherwise>
		</c:choose>	
		<a href="<c:url value="/" />"><spring:message code="menu.home" /></a> |
		<a href="<c:url value="/users" />"><spring:message code="menu.users" /></a> |
		<a href="<c:url value="/profile" />"><spring:message code="menu.profile" /></a> |
		<a href="<c:url value="/logout" />"><spring:message code="user.account.logout" /></a>
	</div>
</div>


<hr />

<div id="content"><tiles:insertAttribute name="body" /></div>

<div id="sidebar">

</div>


<div id="footer">
	<hr />
	&copy; Scrumter 2010
<%-- ${pageContext.request.userPrincipal} --%>
</div>

</div>

</body>
</html>
