<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.bean.DataBean" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <h1>Sample Table</h1>
    
    <form action="PracticeServlet" method="post">
        <input type="hidden" name="action" value="add">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required>
        <label for="value">Value:</label>
        <input type="text" id="value" name="value" required>
        <button type="submit">Add</button>
    </form>

    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Value</th>
            <th>Action</th>
        </tr>
        <c:forEach var="data" items="${dataBeans}">
        <tr>
            <td>${data.id}</td>
            <td>${data.name}</td>
            <td>${data.value}</td>
            <td>
                <form action="PracticeServlet" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="${data.id}">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>