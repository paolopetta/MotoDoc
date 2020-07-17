<%--
  Created by IntelliJ IDEA.
  User: pavil
  Date: 13/07/2020
  Time: 19:33
  To change this template use File | Settings | File Templates.
--%>

<%@  page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="login" method="post">

    <div class="container">
        <input type="text" placeholder="Enter Username" name="uname" id="uname" required>
        <label for="uname"><b>Username</b></label>


        <label for="psw"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="psw" id="psw" required>

        <button type="submit">Login</button>
    </div>


</form>

</body>
</html>
