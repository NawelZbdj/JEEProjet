<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.jeeproject.Model.Professor" %>
<%
    Professor professor = (Professor) request.getAttribute("professor");
%>
<html>
<head>
    <title>Edit Professor</title>
</head>
<body>
<h1>Edit Professor</h1>
<form method="post" action="ProfessorController?action=update">
    <input type="hidden" name="id" value="<%= professor.getId() %>">

    <label for="firstName">First Name:</label>
    <input type="text" id="firstName" name="firstName" value="<%= professor.getFirstName() %>" required><br>

    <label for="lastName">Last Name:</label>
    <input type="text" id="lastName" name="lastName" value="<%= professor.getLastName() %>" required><br>

    <label for="email">Email:</label>
    <input type="email" id="email" name="email" value="<%= professor.getEmail() %>" required><br>

    <label for="specialty">Specialty:</label>
    <input type="text" id="specialty" name="specialty" value="<%= professor.getSpecialty() %>" required><br>

    <label for="birthDate">Birth Date:</label>
    <input type="date" id="birthDate" name="birthDate" value="<%=
       new java.text.SimpleDateFormat("yyyy-MM-dd").format(professor.getBirthDate()) %>" required><br><br>

    <button type="submit">Save Changes</button>
</form>
</body>
</html>
