<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h1><spring:message code="projects" /></h1>
<c:forEach items="${groups}" var="group">
<h2>${group.name}</h2>
managed by ${group.author.firstName} ${group.author.middleName} ${group.author.lastName}
<br />
<c:forEach items="${group.members}" var="member">
${member.firstName} ${member.middleName} ${member.lastName} 
</c:forEach>

</c:forEach>
