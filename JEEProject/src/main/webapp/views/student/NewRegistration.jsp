<%@ page import="com.jeeproject.Model.Registration" %>
<%@ page import="java.util.List" %>
<%@ page import="com.jeeproject.Model.Course" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 11/27/2024
  Time: 1:40 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student - Register for a course</title>
</head>
<body>

<h2>Register for a New Course</h2>

<%
    List<Registration> registrationsList = (List<Registration>) request.getAttribute("registrations");
    List<Course> allCourses = (List<Course>) request.getAttribute("courses");

    if(registrationsList==null){
        System.out.println("ERROR 1");
    }else if(allCourses==null){
        System.out.println("ERROR 2");

    }

    List<Integer> registeredCoursesIds = new ArrayList();
    if(registrationsList != null) {
        for (Registration registration : registrationsList) {
            registeredCoursesIds.add(registration.getCourse().getId());
        }
    }

    Map<String,List<Course>> coursesBySpecialities = new HashMap<>();
    for(Course course : allCourses){
        if(!coursesBySpecialities.containsKey(course.getSpeciality())){
            coursesBySpecialities.put(course.getSpeciality(),new ArrayList<>());
        }
        coursesBySpecialities.get(course.getSpeciality()).add(course);
    }

    for(Map.Entry<String,List<Course>> coursesBySpeciality : coursesBySpecialities.entrySet()){
        String speciality = coursesBySpeciality.getKey();
        List<Course> specialityCourses = coursesBySpeciality.getValue();

%>
    <h3><%=speciality%></h3>
    <table>
        <tr>
            <th>Course</th>
            <th>Description</th>
            <th>Credit</th>
            <th>Action</th>
        </tr>
        <%
            for(Course course : specialityCourses){
        %>
        <tr>
            <td><%=course.getTitle()%></td>
            <td><%=course.getDescription()%></td>
            <td><%=course.getCredit()%></td>
            <%
                if(registeredCoursesIds.contains(course.getId())){
            %>
              <td>Already registered</td>

            <%
                }else{
            %>
                <td><a href="<%= request.getContextPath() %>/RegistrationController?action=add&courseId=<%=course.getId()%>"
                onclick="return confirm('Are you sure you want to register for <%=course.getTitle()%> ?')">Register</a></td>
            <%
                }
            %>
        </tr>
        <%
            }
        %>
    </table>


<%
    }
%>
</body>
</html>
