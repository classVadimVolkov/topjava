<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meal</title>
</head>
<body>

<h3><a href="index.html">Home</a></h3>
<hr>
<h2><c:out value="${empty meal.id ? 'Add' : 'Edit'}"/> meal</h2>

<form method="POST" action="meals">
    <input type="hidden" name="id" value="${meal.id}"/>
    <table>
        <tbody>
        <tr>
            <td>DateTime:</td>
            <td>
                <input
                        type="text" name="dateTime"
                        value="<c:out value="${empty meal.id ? 'yyyy-MM-dd HH:mm' : dateFormatter.format(meal.dateTime)}" />"/>
            </td>
        </tr>
        <tr>
            <td>Description:</td>
            <td>
                <input
                        type="text" name="description"
                        value="<c:out value="${meal.description}" />"/>
            </td>
        </tr>
        <tr>
            <td>Calories:</td>
            <td><input
                    type="text" name="calories"
                    value="<c:out value="${meal.calories}" />"/>
            </td>
        </tr>
        </tbody>
    </table>
    <br>
    <input type="submit" value="Submit"/>
    <button type="button" onclick="window.history.back();">Cancel</button>
</form>

</body>
</html>
