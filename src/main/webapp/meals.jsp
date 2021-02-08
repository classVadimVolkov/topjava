<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style type="text/css">
        .excess {
            color: red;
        }

        .normal {
            color: green;
        }

        table, th, td {
            border: 2px solid darkgrey;
            border-collapse: collapse;
        }
    </style>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach items="${meals}" var="meal">
            <tr class="color: ${meal.excess ? 'excess' : 'normal'}">
                <td><c:out value="${dateTimeFormatter.format(meal.dateTime)}" /></td>
                <td><c:out value="${meal.description}" /></td>
                <td><c:out value="${meal.calories}" /></td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>
