<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理者画面</title>
<link rel="stylesheet" href="./resources/css/master.css">
</head>
<body>
	<header><h1>管理者画面</h1></header>
	<main>
		<div>${message}</div>
		<div>
			<c:if test="${!empty tweetsList}">
				<c:forEach var="tweets" items="${tweetsList}">
					<p>0
						<c:out value="${tweets.name}" />:
						<c:out value="${tweets.comment}" />
					</p>
					<div>
						<form:form modelAttribute="tweetModel">
							<form:hidden path="id" value="${tweets.id}" />
							<input type="submit" name="btn" value="削除">
						</form:form>
					</div>
				</c:forEach>
			</c:if>
		</div>
	</main>
	<a href="/tweet/tweet">tweetに戻る</a>
</body>
</html>