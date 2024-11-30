<%@ page import="com.jeeproject.Model.Course" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Professor Courses</title>
</head>
<body>

<h2>Courses List</h2>

<ul>
    <%

        List<com.jeeproject.Model.Course> courses = (List<Course>) request.getAttribute("coursesList");

        if (courses != null && !courses.isEmpty()) {

            for (com.jeeproject.Model.Course course : courses) {
    %>
    <li>
        <form action="<%= request.getContextPath() %>/StudentController" method="POST">
            <input type="hidden" name="action" value="listByCourses">
            <input type="hidden" name="courseId" value="<%= course.getId() %>">
            <button type="submit" class="link-button">
                <%= course.getTitle() %>
            </button>
        </form>
    </li>
    <%
        }
    } else {
    %>
    <li>No courses.</li>
    <%
        }
    %>
</ul>

</body>
</html>
