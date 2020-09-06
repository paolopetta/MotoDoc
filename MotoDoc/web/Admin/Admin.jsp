<%--
  Created by IntelliJ IDEA.
  User: pavil
  Date: 23/07/2020
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title>
    <link rel="stylesheet" href="../StyleAdmin.css">
</head>
<body>
<%@ include file= "../_header.jsp" %>

<div class="container">
<form action="${pageContext.request.contextPath}/AdminServlet?action=insert" method="POST">
    <fieldset>
        <label>Nome:</label><br>
        <input name="name" type="text" maxlength="20" placeholder="Inserisci nome" required><br>

        <label>Codice:</label><br>
        <input name="codProd" type="number" maxlength="5" placeholder="Inserisci codice" required><br>

        <label>Link img:</label><br>
        <input name="img" type="text" maxlength="100" placeholder="Inserisci link img" required><br>

        <label>Descrizione:</label><br>
        <textarea name="description" maxlength="100" rows="3" placeholder="Inserisci la descrizione" required></textarea><br>

        <label>Prezzo:</label><br>
        <input name="price" type="number" min="0" value="0" required><br>

        <label>Marca:</label><br>
        <input name="brand" type="text" maxlength="20" placeholder="Inserisci Marca" required><br>

        <label>Disponibilitá:</label><br>
        <input name="availability" type="text" maxlength="1" placeholder="Inserisci Disponibilitá (y o n)" required><br>

        <label>Offerta:</label><br>
        <input name="offer" type="text" maxlength="1" placeholder="Inserisci Offerta (y o n)" required><br>

        <label>Quantitá:</label><br>
        <input name="quantity" type="number" min="1" value="1" required><br>

        <input type="submit" value="Inserisci">
        <input type="reset" value="Reset">
    </fieldset>
</form>
</div>
<%@ include file= "../_footer.jsp" %>
</body>
</html>
