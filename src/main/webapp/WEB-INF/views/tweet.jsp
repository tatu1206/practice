<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Tweet</title>
	<link rel="stylesheet" href="./resources/css/tweet.css">
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Ysabeau+SC&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css2?family=Yuji+Syuku&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css2?family=M+PLUS+2&display=swap" rel="stylesheet">
</head>
<body>
	<header>
		<a href="/tweet/top"><img alt="タイトル" src="resources/img/touka.png"></a>
		<h1>～レビュー画面～</h1>
		<div id="master">
			<a href="tweet/login">マスター画面へ</a>
		</div>
	</header>
	<main>
		<div class="top">
			<div class="picture">
				<c:forEach var="animesList" items="${animeList}">
					<img alt="写真" src="${animesList.file}">
				</c:forEach>
			</div>
			<section class="titlePro">
				<c:forEach var="animesList" items="${animeList}">
					<h2>${animesList.title}</h2>
					<p>${animesList.profile}</p>
				</c:forEach>
			</section>
		</div>

		<div>${message}</div>
		<div id="main">
			<c:if test="${!empty tweetsList}">
				<ol>
					<c:forEach var="tweets" items="${tweetsList}">
						<li><p>
							<c:out value="${tweets.name}" />:
							<c:out value="${tweets.comment}" />
							</p>
							<hr>
						</li>
					</c:forEach>
				</ol>
			</c:if>
		</div>
		<div class="tweet">
			<form:form modelAttribute="tweetModel">
				<p class="input">
					名前：<form:input path="name" />
					コメント<form:input path="comment" />
				</p>
				<div class="input">
					<form:hidden path="title" value="${inputModel.title}"/>
					<input type="submit" value="登録" class="btn">
				</div>
				<p class="input">
					<form:errors path="name" element="span" cssClass="errors"/>
					<form:errors path="comment" element="span" cssClass="errors"/>
				</p>
				<P>${message2}</P>
			</form:form>
		</div>
	</main>
</body>
</html>