<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.jeeproject.Model.Administrator" %>
<%
    Administrator admin = (Administrator) request.getAttribute("admin");
%>
<html>
<head>
    <title>Edit Administrator</title>
</head>
<body>
<h1>Edit Administrator</h1>
<form method="post" action="AdminController?action=update">
    <input type="hidden" name="id" value="<%= admin.getId() %>">

    <label for="firstName">First Name:</label>
    <input type="text" id="firstName" name="firstName" value="<%= admin.getFirstName() %>" required><br>

    <label for="lastName">Last Name:</label>
    <input type="text" id="lastName" name="lastName" value="<%= admin.getLastName() %>" required><br>

    <label for="email">Email:</label>
    <input type="email" id="email" name="email" value="<%= admin.getEmail() %>" required><br>

    <label for="position">Position:</label>
    <input type="text" id="position" name="position" value="<%= admin.getPosition() %>" required><br>

    <label for="birthDate">Birth Date:</label>
    <input type="date" id="birthDate" name="birthDate" value="<%=
       new java.text.SimpleDateFormat("yyyy-MM-dd").format(admin.getBirthDate()) %>" required><br><br>

    <button type="submit">Save Changes</button>
</form>
</body>
</html>
