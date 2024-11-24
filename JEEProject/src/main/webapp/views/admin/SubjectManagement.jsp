<%@ page import="com.jeeproject.Model.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>

<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 11/22/2024
  Time: 8:23 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin - Subject Management</title>
</head>
<body>
<%
    if(request.getAttribute("courses") == null) {
        response.sendRedirect(request.getContextPath() + "/CourseController?action=list&destination=/views/admin/SubjectManagement.jsp");
    }

            if(request.getAttribute("courses")!=null){
                List<Course> courses = (List<Course>) request.getAttribute("courses");

                //Organize by specialities
                Map<String,List<Course>> coursesBySpecialities = new HashMap<>();
                for(Course course : courses){
                    if(!coursesBySpecialities.containsKey(course.getSpeciality())){
                        coursesBySpecialities.put(course.getSpeciality(),new ArrayList<>());
                    }
                    coursesBySpecialities.get(course.getSpeciality()).add(course);
                }

                for(Map.Entry<String, List<Course>> CoursesBySpeciality : coursesBySpecialities.entrySet()){
                    List<Course> specialityCourses = CoursesBySpeciality.getValue();
%>
    <h2><%=CoursesBySpeciality.getKey()%></h2>
        <%        for(Course course : specialityCourses){
        %>
    <table>
        <tr>
            <td><%= course.getTitle()%></td>
            <td><%= course.getDescription()%></td>
            <td><%= course.getCredit()%></td>
            <td><a href="<%=request.getContextPath()%>/CourseController?action=edit&id=<%= course.getId() %>">edit</a></td>
            <td><a href="<%=request.getContextPath()%>/CourseController?action=delete&id=<%= course.getId() %>" onclick="return confirm('Are you sure you want to delete this course?');">
                delete</a></td>
        </tr>
    </table>

<%
                }
            }
        }
%>
    <br><br>
    <a href="<%=request.getContextPath()%>/views/admin/AddCourse.jsp">add course</a>

</body>
</html>
