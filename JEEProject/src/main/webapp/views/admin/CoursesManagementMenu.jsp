<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 11/18/2024
  Time: 3:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin - Courses Management Menu</title>
</head>
<body>
<table>
    <tr>
        <td><a href="SubjectManagement.jsp">Subject Management</a></td>
    </tr>
    <tr>
        <td><a href="<%=request.getContextPath()%>/RegistrationController?action=listall&destination=/views/admin/CourseAssignment.jsp">
            Course Assignment
        </a>
        </td>
    </tr>
</table>

</body>
</html>
