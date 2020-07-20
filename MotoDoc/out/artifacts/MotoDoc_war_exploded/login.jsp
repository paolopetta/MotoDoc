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
    <!--https://www.tutorialrepublic.com/codelab.php?topic=bootstrap-3&file=simple-login-form-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        .body{
            background-color: #ce6e31;
        }
        .login-form {
            width: 340px;
            margin: 50px auto;
        }
        .login-form form {
            margin-bottom: 15px;
            background: #f7f7f7;
            box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
            padding: 30px;
        }
        .login-form h2 {
            margin: 0 0 15px;
        }
        .form-control, .btn {
            min-height: 38px;
            border-radius: 2px;
        }
        .btn {
            font-size: 15px;
            font-weight: bold;
        }
    </style>
</head>
<body>
<%@ include file= "_header.jsp" %>
<link rel="stylesheet" href="Style.css">

<div class="login-form">
    <form action="login?action=login" method="post">
        <h2 class="text-center">Log in</h2>
        <div class="form-group">
            <label for="email"><b>Username</b></label>
            <input type="text" class="form-control" placeholder="Inserisci l'email" name="email" id="email" required>
        </div>
        <div class="form-group">
            <label for="password"><b>Password</b></label>
            <input type="password" class="form-control" placeholder="Inserisci la Password" name="password" id="password" required>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block">Log in</button>
        </div>
    </form>
    <p class="text-center"><a href="#">Create an Account</a></p>
</div>
</body>
</html>
