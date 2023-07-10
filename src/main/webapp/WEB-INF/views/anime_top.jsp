<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="./resources/css/top.css">
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Ysabeau+SC&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css2?family=Yuji+Syuku&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css2?family=M+PLUS+2&display=swap" rel="stylesheet">

	<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
	<link rel="stylesheet" href="./resources/css/slick-custom.css">
	<script type="text/javascript" src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>

</head>
<body>
	<header>
		<img alt="タイトル" src="resources/img/touka.png">
		<h1>~SELECT~</h1>
	</header>
	<div class="img-contain">
		<c:if test="${!empty animeList}">
			<c:forEach var="animesList" items="${animeList}">
			<div class="anime-contain">
				<a href="/tweet/${animesList.title}"><img src="${animesList.file}" alt="ねこ"></a>
				<h2><c:out value="${animesList.title}" /></h2>
			</div>
			</c:forEach>
		</c:if>
	</div>
	<main>
		<div class="contain">
			<c:if test="${!empty animeList}">
			<c:forEach var="animesList" items="${animeList}">
			<div class="anime-contain">
				<a href="/tweet/${animesList.title}"><img src="${animesList.file}" alt="ねこ"></a>
				<h2><c:out value="${animesList.title}" /></h2>
				<p><c:out value="${animesList.profile}" /></p>
			</div>
			</c:forEach>
			</c:if>
		</div>
	</main>
	<script src="//code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
	<script>
	$('.img-contain').slick({
		  slidesToShow: 3,
		  slidesToScroll: 1,
		  autoplay: true,
		  autoplaySpeed: 2000,
	      nextArrow: '<button type="button" class="slick-next">Next</button>'
		});
	</script>
</body>
</html>