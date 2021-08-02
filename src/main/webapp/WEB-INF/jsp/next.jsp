<%--
  Created by IntelliJ IDEA.
  User: Dima
  Date: 29.07.2021
  Time: 20:33
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Игра "города"</title>
</head>
<body>

<p>Вы назвали город: </p>
<p>${userCityNamePrevious}</p>

<c:if test="${error == null}">
    <p></p>
    <p>Я называю город:</p>
    <p>${pcCityName}</p>
</c:if>
<c:if test="${error != null}">
    <p>${error}</p>
    <p>ранее я назвал город:</p>
    <p>${pcCityName}</p>
</c:if>
<div>
    <form method="post" action="/next">
        <label for="nam">Назовите город: </label>
        <input id="nam" type="text" name="userCityName" maxlength="35" required/>
        <input type="submit" value="Назвать">
        <input type="hidden" name="pcCityNamePrevious" value="${pcCityName}">
    </form>
</div>
<button onclick="location.href='/giveUp'" >Завершить игру</button>

</body>
</html>
