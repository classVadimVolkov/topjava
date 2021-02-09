<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <style type="text/css">
        table, th, td {
            border: 2px solid darkgrey;
            border-collapse: collapse;
        }

        .excess {
            color: red;
        }

        .norm {
            color: green;
        }
    </style>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<h3><a href="?action=add">Add meal</a> </h3>
<br>
<table>
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
        <c:forEach items="${meals}" var="meal">
            <tr class="color: ${meal.excess ? 'excess' : 'norm'}">
                <td><c:out value="${dateFormatter.format(meal.dateTime)}" /></td>
                <td><c:out value="${meal.description}" /></td>
                <td><c:out value="${meal.calories}" /></td>
                <td><a href="?id=${meal.id}&action=update">Update</a></td>
                <td><a href="?id=${meal.id}&action=delete">Delete</a></td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>
