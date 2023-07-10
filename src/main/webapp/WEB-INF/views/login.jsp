<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理者画面</title>
</head>
<body>
	<header><h1>管理者画面</h1></header>
	<form:form modelAttribute="loginModel">
	<table border="1">
		<tr>
			<th>ID</th>
			<th>パスワード</th>
		</tr>
		<tr>
			<td>
				<form:input path="id" />
				<form:errors path="id" />
			</td>
			<td>
				<form:password path="password" />
				<form:errors path="password" />
			</td>
		</tr>
	</table>
	<input type="submit" value="ログイン">
	<p>${message}</p>
	</form:form>
</body>
</html>