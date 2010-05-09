<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<title>Scrumter</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/css/screen.css" />" />
<link type="text/css" rel="stylesheet" href="<c:url value="/css/lib/jquery.multiselect.css" />" />
<link type="text/css" rel="stylesheet" href="<c:url value="/css/lib/south-street/jquery-ui-1.8.custom.css" />" />

<script type="text/javascript" src="<c:url value="/js/lib/jquery-1.4.2.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/lib/jquery-ui-1.8.custom.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/lib/jquery.multiselect.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/lib/autoresize.jquery.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/lib/prettydate.js" />"></script>

<script type="text/javascript">
scrumter = {
	strings: {
		statusFormTitle: '<spring:message code="status.form.title" />'
	}
}

function doPrettyDates() {
	$(".date").prettyDate();
}

function minimizable(input) {
	$(input).focus(function() {
		$(this).parents(".minimizable").removeClass("minimized");
		$(this).parents(".minimizable .unchanged").find("textarea").val('');
	});
	$(input).blur(function() {
		$(this).parents(".unchanged").parents(".minimizable").addClass("minimized");
		$(this).parents(".minimizable form.unchanged").each(function() {
			this.reset();
		});
	});
	$(input).change(function() {
		$(this).parents(".minimizable form").removeClass("unchanged");
	});
}
jQuery.fn.minimizable = function(){
	return this.each(function(){
		minimizable(this);
	});
};

$(document).ready(function(){
	doPrettyDates();
	setInterval(function(){ doPrettyDates(); }, 10000);
	$("button, input:submit").button();
	$("#allowed").multiSelect({
		noneSelectedText: 'Select audiences',
		selectedText: function(numChecked, numTotal, checkedItems) {
			if (numChecked < 4) {
				var list = $(checkedItems).map(function(){ return this.title; }).get().join(', ');
				return "Audiences: " + list;
			} else {
				return numChecked + ' audiences selected';
			}
		},
		selectedList: 2,
		minWidth: 440
	});
	$("#status-form textarea").autoResize();

	$("#status-form").submit(function(){
		if ($("#status-form :checked").length == 0) {
			alert("Please select audiences");
			return false;
		}
		$.post('<c:url value="/api/status/add" />', $("#status-form").serialize(), function(data) {
			var post = $(data).hide().prependTo('.posts');
			doPrettyDates();
			post.slideDown("slow");
			$("#status-form-textarea").height(25);
			$("#status-form")[0].reset();
		});
		return false;
	});

	$("#notification-dismissall").click(function () {
		$.post('<c:url value="/api/notification/dismiss-all" />', function(data) {
			console.log(data);
			if (data.success) {
				$("#notifications .notification").fadeOut();
			}
		});
		return false;
	});


	$(".posts").delegate(".write-comment form", "submit", function() {
		var form = this;
		var comments = $(this).parents(".status").find(".comments");
		$.post(this.action, $(this).serialize(), function(data) {
			var post = $(data).hide().appendTo(comments);
			doPrettyDates();
			post.slideDown("slow");
			$(form).addClass("unchanged").parents(".minimizable").addClass("minimized");
			$(form)[0].reset();
		});
		return false;
	});

	$(".minimizable textarea").minimizable();
	
});
</script>

</head>
<body>
<div id="header">
	<a href="<c:url value="/" />"><img src="<c:url value="/img/logo.png" />" /></a>
	<div id="user-nav">
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
<div id="container">

<div id="navbar"><tiles:insertDefinition name="menu/home" /></div>

<div id="content"><tiles:insertAttribute name="body" /></div>

<div id="sidebar">
	<tiles:insertDefinition name="notification/list" />
</div>

</div>

<div id="footer">Powered by Scrumter | Knowledge is its own reward</div>

</body>
</html>
