<%@ page import="com.jeeproject.Model.Professor" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Professor Management</title>
</head>
<body>
<h1>Professor Management</h1>

<form method="get" action="ProfessorController">
    <input type="hidden" name="action" value="search">
    <label for="keyword">Search:</label>
    <input type="text" name="keyword" id="keyword" placeholder="Name or email">
    <label for="specialty">Specialty:</label>
    <select name="specialty" id="specialty">
        <option value="">All</option>
        <option value="computerScience">Computer Science</option>
        <option value="Art">Art</option>
    </select>
    <button type="submit">Search</button>
</form>

<a href="<%=request.getContextPath()%>/ProfessorController?action=add">Add New Professor</a>



<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Email</th>
        <th>Specialty</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
        if(request.getAttribute("professors") == null) {
            response.sendRedirect(request.getContextPath() + "/ProfessorController?action=list");
        }
        else{
        List<Professor> professors = (List<Professor>) request.getAttribute("professors");

        if (professors != null && !professors.isEmpty()) {
            for (Professor professor : professors) {
    %>
    <tr>
        <td><%= professor.getId() %></td>
        <td><%= professor.getFirstName() %></td>
        <td><%= professor.getLastName() %></td>
        <td><%= professor.getEmail() %></td>
        <td><%= professor.getSpecialty() %></td>
        <td>
            <a href="<%=request.getContextPath()%>/ProfessorController?action=update&id=<%= professor.getId() %>&destination=/views/admin/UpdateProfessor.jsp">Edit</a> |
            <a href="ProfessorController?action=delete&id=<%= professor.getId() %>" onclick="return confirm('Are you sure?')">Delete</a>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="6">No professors found.</td>
    </tr>
    <%
        }
        }
    %>
    </tbody>
</table>

</body>
</html>