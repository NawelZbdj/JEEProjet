<%@ page import="com.jeeproject.Model.Student" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Student Management</title>
</head>
<body>
<h1>Student Management</h1>

<form method="get" action="StudentController">
    <input type="hidden" name="action" value="search">
    <label for="keyword">Search:</label>
    <input type="text" name="keyword" id="keyword" placeholder="Name or email">
    <button type="submit">Search</button>
</form>

<a href="<%=request.getContextPath()%>/StudentController?action=add">Add New Student</a>



<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Email</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
        if(request.getAttribute("students") == null) {
            response.sendRedirect(request.getContextPath() + "/StudentController?action=list");
        }
        else{
            List<Student> students = (List<Student>) request.getAttribute("students");

            if (students != null && !students.isEmpty()) {
                for (Student student : students) {
    %>
    <tr>
        <td><%= student.getId() %></td>
        <td><%= student.getFirstName() %></td>
        <td><%= student.getLastName() %></td>
        <td><%= student.getEmail() %></td>
        <td>
            <a href="<%=request.getContextPath()%>/StudentController?action=update&id=<%= student.getId() %>&destination=/views/admin/UpdateStudent.jsp">Edit</a> |
            <a href="StudentController?action=delete&id=<%= student.getId() %>" onclick="return confirm('Are you sure?')">Delete</a>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="6">No students found.</td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>

</body>
</html>