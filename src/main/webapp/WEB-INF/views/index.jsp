<%@ page import="my.thereisnospoon.pheebo.aop.HitCounter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<title>Pheebo</title>
</head>
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<body>
	<h1>Hits: <%= HitCounter.hits.toString() %></h1>
</body>
</html>
