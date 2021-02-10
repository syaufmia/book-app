<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="head-navbar.jsp" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html lang="en">

<body>

<main>
    <div class="text-box">
        <h1>Welchen Autor möchtest du löschen?</h1>
        <p>Wähle eine Option.</p>
        <p class="red">${message}</p>
        <p class="grey"> ${sentence} </p>

        <form class="form" name="select-author" action= "${pageContext.request.contextPath}/deleted-author/" method="POST">
            <table class="form-table"border="0">

                ${requestScope.htmltext}


            </table>
            <p class="grey">
                Wähle einen Autor aus oder gib einen neuen Autor ein. </p>
            <input type="hidden" name="name" value="${name}">
            <input class="button" type="submit" value="Autor auswählen" name="submit" />

        </form>
        <form name="return" action="index.jsp" method="POST">

        <input class="button" type="submit" value="Zurück zu Anfang" name="submit" />
        </form>
    </div>

</main>

</body>
</html>