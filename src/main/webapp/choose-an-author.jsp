<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="head-navbar.jsp" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html lang="en">

<body>
<main>
    <div class="text-box">
        <h1>Wer ist der Autor des Buches?</h1>
        <p>Wähle eine Option.</p>
        <p class="red">${message}</p>
        <p class="grey"> ${sentence} </p>

        <form class="form" name="select-author" action= "added-book" method="POST">
            <table class="form-table"border="0">

            <%
            out.println(request.getAttribute("html-text"));
            %>

            </table>
            <p class="grey">
                Wähle einen Autor aus oder gib einen neuen Autor ein. </p>
            <input type="hidden" name="titel" value="${titel}">
            <input type="hidden" name="isbn" value="${isbn}">
            <input type="hidden" name="publisher" value="${publisher}">
            <input type="hidden" name="year" value="${year}">
            <input type="hidden" name="name" value="${name}">
            <input class="button" type="submit" value="Autor auswählen" name="submit" />

        </form>
        <form name="add-author" action="added-author" method="POST">
        <input type="hidden" name="titel" value="${titel}">
        <input type="hidden" name="isbn" value="${isbn}">
        <input type="hidden" name="publisher" value="${publisher}">
        <input type="hidden" name="year" value="${year}">
        <input class="button" type="submit" value="Stattdessen einen neuen Autor eingeben" name="submit" />
        </form>
    </div>


</main>

</body>
</html>