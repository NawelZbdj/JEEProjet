<%@ page import="com.jeeproject.Model.Course" %><%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 11/23/2024
  Time: 12:09 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin - Edit Course</title>
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
    <%
      Course course = (Course) request.getAttribute("course");
    %>
    <h2>Edit a Course</h2>
    <form action="<%=request.getContextPath()%>/CourseController" method="post" class="formAff">
      <label>Title : </label>
      <input type="text" id="title" name="title" value="<%=course.getTitle()%>"><br><br>

      <label>Description : </label>
      <input type="text" id="description" name="description" value="<%=course.getDescription()%>"><br><br>

      <label>Credit : </label>
      <input type="text" id="credit" name="credit" value="<%=course.getCredit()%>"><br><br>

      <label>Speciality : </label>
      <input type="text" id="speciality" name="speciality" value="<%=course.getSpeciality()%>"><br><br>

      <input type="hidden" name="action" value="update">
      <input type="hidden" name="id" value="<%=course.getId()%>">

      <button type="submit">Save Changes</button>

    </form>
    </main>
</div>
</body>
</html>
