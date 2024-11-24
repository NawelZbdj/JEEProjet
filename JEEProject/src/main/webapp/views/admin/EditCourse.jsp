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
</head>
<body>
    <%
      Course course = (Course) request.getAttribute("course");
    %>
    <h2>Edit a Course</h2>
    <form action="<%=request.getContextPath()%>/CourseController" method="post">
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

</body>
</html>