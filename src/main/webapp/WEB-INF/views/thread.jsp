<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<title>/${thread.board.path}/ ${thread.header}</title>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="/resources/css/thread.css">
</head>
<body>
<div class="content">
	<%@ include file="boards.jspf" %>
	<div class="thread">
		<h2 class="centred">${thread.header}</h2>
		<hr class="centred"/>
		<c:forEach var="post" items="${thread.posts}">
			<%@include file="post.jspf" %>
		</c:forEach>
		<form:form commandName="post" method="post">
			<form:textarea path="message"/>
			<form:errors path="message"/>
			<input type="submit" value="Send">
		</form:form>
	</div>
</div>
<%@include file="footer.jspf" %>
</body>
</html>
