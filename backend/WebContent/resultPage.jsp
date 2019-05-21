<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Shop</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<div id="wrapper">
<div id="header">
</div>
<div id="content">
	<table id="result_table">
		<c:forEach items="${sessionScope.result}" var="name">
			<tr>
				<td><a href="${name}.html">${name}</a></td>

			</tr>
		</c:forEach>
	</table>
	
</div>
<div id="footer">
</div>
</body>
</html>