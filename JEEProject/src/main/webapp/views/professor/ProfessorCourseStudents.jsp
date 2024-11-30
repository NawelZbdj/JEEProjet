<%@ page import="com.jeeproject.Model.Student" %>
<%@ page import="com.jeeproject.Model.Course" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Course Students</title>
</head>
<body>

<%

    Course course = (Course) request.getAttribute("course");
    String courseTitle = course != null ? course.getTitle() : "Unknown Course";
%>

<h1>Students in <%= courseTitle %></h1>

<%

    List<Student> students = (List<Student>) request.getAttribute("students");
%>

<%
    if (students != null && !students.isEmpty()) {
%>
<h2>List of Students:</h2>

    <%

        for (Student student : students) {
    %>
        <form action="<%= request.getContextPath() %>/ResultController" method="POST">
            <input type="hidden" name="action" value="viewGrades">
            <input type="hidden" name="studentId" value="<%= student.getId() %>">
            <input type="hidden" name="courseId" value="<%= course.getId() %>">
            <button type="submit">
                <%= student.getFirstName() %> <%= student.getLastName() %>
            </button>
        </form>

    <%
        }
    %>

<%
} else {
%>
<p>No students are enrolled in this course.</p>
<%
    }
%>

<br><a href="CourseController?action=listByProfessor">Back to Courses</a>


</body>
</html>
