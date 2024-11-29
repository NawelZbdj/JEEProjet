<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 11/28/2024
  Time: 11:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin log</title>
    <link rel="stylesheet" href="../css/AdminStyle.css">
</head>
<body>
<div class="page">
    <header class="banner">
        <img src="../image/logoGreen.png" alt="Logo" class="banner-image" onclick="">
    </header>
    <main class="content">
        <h1>Log in</h1>
        <form action="<%=request.getContextPath()%>/AccountController" method="post" class="log">
            <label>Username :</label>
            <input type="text" name="username" id="username" required><br>
            <label>Password :</label>
            <input type="password" name="password" id="password" required><br>
            <input type="hidden" name="role" value="admin">
            <input type="hidden" name="action" value="connect">
            <button type="submit">Connect</button>
        </form>
    </main>
</div>
</body>
</html>
