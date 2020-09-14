<%--
  Created by IntelliJ IDEA.
  User: pavil
  Date: 27/07/2020
  Time: 18:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_header.jsp" %>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
<link rel="stylesheet" href="Style.css">
<link href="./ProductStyle.css" rel="stylesheet" type="text/css">
</head>
<body>
<style>
    .w3-container{ margin: 15px 50px;
        line-height: 2.0;
    }
    strong{ font-style: italic;
        color: #363a40;}
</style>

<!-- Add a background color and large text to the whole page -->
<div class="w3-sand w3-grayscale w3-large">
    <br><br>


    <!-- Registration container -->
    <section class="w3-container">
        <div class="w3-content" style="max-width:800px">
            <h5 class="w3-center w3-padding-48">
                <span class="w3-tag w3-wide" style="color: orangered; margin: 50px" >REGISTRATI</span>
            </h5>
            <div class="w3-container w3-padding-48 w3-card">
                <div class="w3-content">
                    <p>
                		<span>
                			<strong >Compila tutti i campi</strong>
                		</span>
                    </p>
                    <p>
                    <form action="register" method="POST" name="registration">
                        <span>Username (min 6 caratteri, solo lettere e numeri)</span>
                        <span>
                				<input class="w3-input w3-padding-16" type="w3-text" placeholder="Username.." name="username" oninput="validaUsername()">
                        </span><br>
                        <span>Password (min 8 caratteri; almeno un carattere minuscolo e maiuscolo e un numero)</span>
                        <span>
                				<input class="w3-input w3-padding-16" type="password" placeholder="Password.." name="password" oninput="validaPassword()">
                			</span><br>
                        <span>Password (conferma)</span>
                        <span>
                				<input class="w3-input w3-padding-16" type="password" placeholder="Password.." name="passwordConferma" oninput="validaPassword()">
                			</span><br>
                        <span>Nome (solo lettere e numeri)</span>
                        <span>
                				<input class="w3-input w3-padding-16" type="w3-text" placeholder="Nome.." name="nome" oninput="validaNome()">
                			</span><br>
                        <span>E- mail </span>
                        <span>
                				<input class="w3-input w3-padding-16" type="w3-text" placeholder="pippo@pippo.pippo" name="email" oninput="validaEmail()">
                			</span><br>
                        <span>
                				<button class="w3-button w3-black" id="registrami" type="submit" disabled>REGISTRATI</button>
                			</span>
                        <span id="registramimessaggio"></span>
                    </form>
                    </p>
                </div>
            </div>
        </div>
    </section>
</div>
<script src="${pageContext.servletContext.contextPath}/js/Validate.js"></script>
</body>