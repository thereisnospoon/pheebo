<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>/${thread.board.path}/ ${thread.header}</title>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="/resources/css/main.css">
</head>
<body>
<%@ include file="boards.jspf" %>
<div class="content">
	<div class="thread">
		<h2 class="centred">${thread.header}</h2>
		<hr class="centred"/>
		<c:forEach var="post" items="${thread.posts}">
			<%@include file="post.jspf" %>
		</c:forEach>
	</div>
	<div class="post_form" id="form_${thread.threadId}">
		<textarea placeholder="Message text"></textarea>
		<div id="send-btn">Send</div>
		<div id="post_errors"></div>
	</div>
</div>
<%@include file="footer.jspf" %>
</body>
</html>
