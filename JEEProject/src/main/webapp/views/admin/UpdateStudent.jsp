<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.jeeproject.Model.Student" %>
<%
    Student student = (Student) request.getAttribute("student");
%>
<html>
<head>
    <title>Edit Student</title>
</head>
<body>
<h1>Edit Student</h1>
<form method="post" action="StudentController?action=update">
    <input type="hidden" name="id" value="<%= student.getId() %>">

    <label for="firstName">First Name:</label>
    <input type="text" id="firstName" name="firstName" value="<%= student.getFirstName() %>" required><br>

    <label for="lastName">Last Name:</label>
    <input type="text" id="lastName" name="lastName" value="<%= student.getLastName() %>" required><br>

    <label for="email">Email:</label>
    <input type="email" id="email" name="email" value="<%= student.getEmail() %>" required><br>

    <label for="birthDate">Birth Date:</label>
    <input type="date" id="birthDate" name="birthDate" value="<%=
       new java.text.SimpleDateFormat("yyyy-MM-dd").format(student.getBirthDate()) %>" required><br><br>

    <button type="submit">Save Changes</button>
</form>
</body>
</html>
