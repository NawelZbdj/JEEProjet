
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Admin</title>
</head>
<body>
<form method="post" action="AdminController?action=add" >
    <label for="firstName">First Name:</label>
    <input type="text" id="firstName" name="firstName" required><br>

    <label for="lastName">Last Name:</label>
    <input type="text" id="lastName" name="lastName" required><br>

    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required><br>

    <label for="position">Position:</label>
    <input type="text" id="position" name="position" required><br>
    <label for="birthDate">Birth Date:</label>
    <input type="date" id="birthDate" name="birthDate" required><br><br>
    <button type="submit">Add Administrator</button>
</form>
</body>
</html>