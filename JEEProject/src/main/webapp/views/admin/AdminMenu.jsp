<%@ page import="com.jeeproject.Model.Administrator" %>
<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 11/18/2024
  Time: 1:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin - Courses Management Menu</title>
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
<body>

    <%
    Administrator account = null;
    try {
        account = (Administrator) session.getAttribute("user");
    }
    catch(Exception e){
        account = null;
    }
    if (account==null || !"admin".equals(session.getAttribute("role"))){
        session.invalidate();
%>
<script>
    alert("An issue occurred with the connection.");
    window.location.href = "<%= request.getContextPath() %>/views/menu.jsp";
</script>
    <%
        return;
    }
%>

<h2>Menu</h2>

    <table>
        <tr>
            <td><a class="redirect" href="<%=request.getContextPath()%>/views/admin/AdminManagement.jsp">Administrators</a></td>
        </tr>
        <tr>
            <td><a href="<%=request.getContextPath()%>/views/admin/StudentsManagement.jsp" class="redirect">Students </a></td>
        </tr>
        <tr>
            <td><a href="<%=request.getContextPath()%>/views/admin/ProfessorsManagement.jsp" class="redirect">Professors</a></td>
        </tr>
        <tr>
            <td><a class="redirect" href="<%=request.getContextPath()%>/views/admin/CoursesManagementMenu.jsp">
                Courses
            </a>
            </td>
        </tr>
    </table>
    </main>
</div>
</body>
</html>