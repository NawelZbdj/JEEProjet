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
</head>
<body>
    <h2>Add a new course</h2>
    <form action="<%=request.getContextPath()%>/CourseController" method="post">
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

</body>
</html>
