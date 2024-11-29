<%@ page import="com.jeeproject.Model.Administrator" %>
<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 11/18/2024
  Time: 1:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>


<body>
<%
    Administrator account = (Administrator) session.getAttribute("admin");
    System.out.println(session.getAttribute("admin"));
    if (account!=null) {

%>
<p>Welcome, <%=account.getFirstName()%></p>
<%
    }

%>

<h1>Menu</h1>

    <table>
        <tr>
            <td><a class="redirect" href="<%=request.getContextPath()%>/views/admin/AdminManagement.jsp">Administrators</a></td>
        </tr>
        <tr>
            <td><a href="#" class="redirect">Students</a></td>
        </tr>
        <tr>
            <td><a href=""<%=request.getContextPath()%>/views/admin/ProfessorsManagement.jsp"" class="redirect">Professors</a></td>
        </tr>
        <tr>
            <td><a class="redirect" href="<%=request.getContextPath()%>/views/admin/CoursesManagementMenu.jsp">
                Courses
            </a>
            </td>
        </tr>
    </table>
</body>
</html>