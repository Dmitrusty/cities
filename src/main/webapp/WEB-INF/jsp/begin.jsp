<%--
  Created by IntelliJ IDEA.
  User: Dima
  Date: 29.07.2021
  Time: 20:30
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Игра "Города"</title>
    <link href="styles/styles.css" type="text/css" rel="stylesheet">
</head>
<body class="center">
<p>Начинаем!</p>
<p>Я называю город:</p>
<p>${firstCityName}</p>
<div>
    <form method="post" action="<c:url value="/next"/>">
        <label for="name">Назовите город: </label>
        <input id="name" type="text" name="userCityName" maxlength="35" required/>
        <input type="submit" value="Назвать">
        <input type="hidden" name="serverCityNamePrevious" value="${firstCityName}">
    </form>
</div>
<button onclick="location.href='/giveUp'" >Завершить игру</button>
</body>
</html>
