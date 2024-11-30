<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.jeeproject.Model.Student" %>
<%
    Student student = (Student) request.getAttribute("student");
%>
<html>
<head>
    <title>Edit Student</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/views/css/AdminStyle.css">

</head>
<body>
<div class="page">
    <header class="banner">
        <img src="<%= request.getContextPath() %>/views/image/logoGreen.png" alt="Logo" class="banner-image">
        <button class="logout-button" onclick="logout()">Log out</button>
    </header>
    <script>
        function logout() {
            window.location.href = '<%= request.getContextPath() %>/views/logout.jsp';
        }
    </script>
    <nav class="menu-bar">
        <ul class="menu">
            <li><a href="<%=request.getContextPath()%>/views/admin/AdminManagement.jsp">Administrators</a></li>
            <li><a href="<%=request.getContextPath()%>/views/admin/StudentsManagement.jsp">Students</a></li>
            <li><a href="<%=request.getContextPath()%>/views/admin/ProfessorsManagement.jsp">Professors</a></li>
            <li><a href="<%=request.getContextPath()%>/views/admin/CoursesManagementMenu.jsp">Courses</a></li>
        </ul>
    </nav>
    <main class="content">
        <h1>Edit Student</h1>
<form method="post" action="<%=request.getContextPath()%>/StudentController?action=update" class="formAff">
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
    </main>
</div>
</body>
</html>