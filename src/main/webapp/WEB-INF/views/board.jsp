<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>/${board.path}</title>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="/resources/css/main.css">
</head>
<body>
<div class="content">
	<%@include file="boards.jspf" %>
	<%@include file="pages.jspf" %>
	<c:forEach var="preview" items="${threadPreviews}">
		<div class="thread_preview">
			<a href="/${board.path}/thread/${preview[0].thread.threadId}"
			   class="centred">${preview[0].thread.header}</a>

			<div class="head_post">
				<c:set var="post" value="${preview[0]}"/>
				<%@include file="post.jspf" %>
			</div>
			<div class="last_posts">
				<c:forEach var="post" begin="1" items="${preview}">
					<%@include file="post.jspf" %>
				</c:forEach>
			</div>
			<hr class="centred"/>
		</div>
	</c:forEach>
	<%@include file="pages.jspf" %>
	<div class="post_form">
		<input type="text" placeholder="Thread theme (less then 60 symbols)">
		<textarea placeholder="Message text"></textarea>
		<div id="send-btn">Send</div>
		<div id="post_errors"></div>
        <div class="file_upload">
            <span>Attach image</span>
        </div>
        <form action="/files" method="post" enctype="multipart/form-data">
            <input type="file">
        </form>
	</div>
	<%@include file="footer.jspf" %>
</div>
</body>
</html>
