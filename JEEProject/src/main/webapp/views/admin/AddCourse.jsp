<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 11/23/2024
  Time: 12:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin - Add course</title>
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
        <h2>Add a new course</h2>
    <form action="<%=request.getContextPath()%>/CourseController" method="post" class="formAff">
      <label>Title : </label>
      <input type="text" id="title" name="title"><br><br>

      <label>Description : </label>
      <input type="text" id="description" name="description"><br><br>

      <label>Credit : </label>
      <input type="text" id="credit" name="credit"><br><br>

      <label>Speciality : </label>
      <input type="text" id="speciality" name="speciality"><br><br>

      <input type="hidden" name="action" value="save">
      <button type="submit">Save</button>
    </form>
    </main>
</div>
</body>
</html>
