<%@ page import="com.jeeproject.Model.Administrator" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Admin Management</title>
</head>
<body>
<h1>Admin Management </h1>

<form method="get" action="AdminController">
    <input type="hidden" name="action" value="search">
    <label for="keyword">Search:</label>
    <input type="text" name="keyword" id="keyword" placeholder="Name or email">
    <label for="pos">Position:</label>
    <select name="position" id="pos">
        <option value="">All</option>
        <option value="Manager">Manager</option>
        <option value="Developer">Developer</option>
    </select>
    <button type="submit">Search</button>
</form>

<a href="AdminController?action=add">Add New Administrator</a>



<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Email</th>
        <th>Position</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<Administrator> administrators = (List<Administrator>) request.getAttribute("administrators");

        if (administrators != null && !administrators.isEmpty()) {
            for (Administrator admin : administrators) {
    %>
    <tr>
        <td><%= admin.getId() %></td>
        <td><%= admin.getFirstName() %></td>
        <td><%= admin.getLastName() %></td>
        <td><%= admin.getEmail() %></td>
        <td><%= admin.getPosition() %></td>
        <td>
            <a href="AdminController?action=update&id=<%= admin.getId() %>">Edit</a> |
            <a href="AdminController?action=delete&id=<%= admin.getId() %>" onclick="return confirm('Are you sure?')">Delete</a>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="6">No administrators found.</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

</body>
</html>