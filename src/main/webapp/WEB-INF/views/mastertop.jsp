<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理者画面</title>
<link rel="stylesheet" href="./resources/css/top.css">
</head>
<body>
	<header><h1>アニメ掲示板（管理）</h1></header>
	<main>
		<div>
			<h2>新規アニメ登録</h2>
			<form:form modelAttribute="newAnimeModel" enctype="multipart/form-data">
				<input type="file" name="file" accept=".jpg" />
				タイトル：<form:input path="title" />
				紹介文：<form:input path="profile" />
				<input type="submit" name="btn" value="登録">
			</form:form>
			<p>${message}</p>
		</div>
		<div class="contain">
			<c:if test="${!empty animeList}">
			<c:forEach var="animesList" items="${animeList}">
			<div class="anime-contain">
				<a href="/tweet/master/${animesList.title}"><img src="${animesList.file}" alt="ねこ"></a>
				<h2><c:out value="${animesList.title}" /></h2>
				<p><c:out value="${animesList.profile}" /></p>
				<form:form modelAttribute="deleteAnimeModel" enctype="application/x-www-form-urlencoded">
					<form:hidden path="title" value="${animesList.title}" />
					<input type="submit" name="btn" value="削除">
				</form:form>
			</div>
			</c:forEach>
			</c:if>
		</div>
	</main>
</body>
</html>