<%@ page import="it.unisa.model.UserBean" %><%--
  Created by IntelliJ IDEA.
  User: pavil
  Date: 02/06/2020
  Time: 09:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    UserBean user = (UserBean) session.getAttribute("user");
%>

<header>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="Style.css">

    <p align="center">
    <img src="https://i.imgur.com/p90VrIH.png" alt="logo MotoDoc" width="400px">
    </p>
    <h6 align="center">Lo shop che ti coccola</h6>

    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <a class="navbar-brand" href="#"><img src="https://i.imgur.com/p90VrIH.png" alt="logo MotoDoc" width="100px"></a>

        <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
            <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                <li class="nav-item active">
                    <a class="nav-link" href="home.jsp">Home <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="ChiSiamo.jsp">Chi Siamo</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
                </li>
            </ul>
            <div id="form" align="center">
            <form class="form-inline my-2 my-lg-0" >
                <input class="form-control mr-sm-2" type="search" placeholder="Inizia la ricerca" aria-label="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Cerca</button>
            </form>
            </div>
            <% if(user != null){%>
            <a class="nav-link" href="#">Ciao <%=user.getNome()%> <span class="sr-only">(current)</span></a>
            <a class="nav-link" href="${pageContext.request.contextPath}/login?action=logout">Logout </a>
            <%}else{%>
            <a class="nav-link" href="login.jsp">Login <span class="sr-only">(current)</span></a>
            <%}%>
        </div>
    </nav>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>

</header>
