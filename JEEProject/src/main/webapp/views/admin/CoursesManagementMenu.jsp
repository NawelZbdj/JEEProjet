<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 11/18/2024
  Time: 3:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin - Courses Management Menu</title>
    <link rel="stylesheet" href="../css/AdminStyle.css">

</head>
<body>
<div class="page">
    <header class="banner">
        <img src="../image/logoGreen.png" alt="Logo" class="banner-image">
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
            <li><a href="">Students</a></li>
            <li><a href="">Professors</a></li>
            <li><a href="<%=request.getContextPath()%>/views/admin/CoursesManagementMenu.jsp">Courses</a></li>
        </ul>
    </nav>
    <main class="content">
<table>
    <tr>
        <td><a href="SubjectManagement.jsp" class="redirect">Subject Management</a></td>
    </tr>
    <tr>
        <td><a class="redirect" href="<%=request.getContextPath()%>/RegistrationController?action=listall&destination=/views/admin/CourseAssignment.jsp">
            Course Assignment
        </a>
        </td>
    </tr>
</table>
    </main>
</div>
</body>
</html>
