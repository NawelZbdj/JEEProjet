<%@ page import="java.util.List" %>
<%@ page import="com.jeeproject.Model.Course" %>
<%@ page import="com.jeeproject.Model.Registration" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 11/25/2024
  Time: 4:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student - Registration Management</title>
</head>
<body>
<h2>My courses</h2>

<%
    if(request.getAttribute("registrations") == null) {
        response.sendRedirect(request.getContextPath() + "/RegistrationController?action=listByStudent&destination=/views/student/RegistrationManagement.jsp");
    }


    if(request.getAttribute("registrations")!=null){
    List<Registration> registrationList = (List<Registration>) request.getAttribute("registrations");

    //get the student id (scope session?)

    //Organize by specialities
    Map<String,List<Registration>> coursesBySpecialities = new HashMap<>();
    for(Registration registration : registrationList){
        String speciality = registration.getCourse().getSpeciality();
        if(!coursesBySpecialities.containsKey(speciality)){
            coursesBySpecialities.put(speciality,new ArrayList<>());
        }
        coursesBySpecialities.get(speciality).add(registration);
    }

%>
    <table>
        <tr>
            <th>Course</th>
            <th>Description</th>
            <th>Credit</th>
            <th> </th>
        </tr>
    </table>
    <%
        for(Map.Entry<String, List<Registration>> coursesBySpeciality : coursesBySpecialities.entrySet()){
            List<Registration> specialityCourses = coursesBySpeciality.getValue();
    %>
    <h3><%=coursesBySpeciality.getKey()%></h3>
        <%
            for(Registration registration : specialityCourses){
                Course currentCourse = registration.getCourse();
        %>
    <table>
        <tr>
            <td><%=currentCourse.getTitle()%></td>
            <td><%=currentCourse.getDescription()%></td>
            <td><%=currentCourse.getCredit()%></td>
            <%
                if(registration.getProfessor()==null){
            %>
            <td>pending</td>
            <td><a href="<%=request.getContextPath()%>/RegistrationController?action=delete&id=<%=registration.getId()%>" onclick="return confirm('Are you sure you want to cancel this registration?');">cancel registration </a></td>
            <%
                }
                else{
            %>
            <td><%=registration.getProfessor().getFirstName()%> <%=registration.getProfessor().getLastName()%></td>
            <%
                }
            %>
        <tr></tr>

    </table>
    <%
            }
        }
        }
    %>
    <a href="<%=request.getContextPath()%>/RegistrationController?action=listByStudentWithCourses&destination=views/student/NewRegistration.jsp">Register for a new course</a>

</body>
</html>
