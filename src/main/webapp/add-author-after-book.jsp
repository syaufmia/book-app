<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="head-navbar.jsp" %>

<!DOCTYPE html>
<html lang="en">

<body>
<main>
    <div class="text-box">
        <h1>Wer ist der Autor des Buches?</h1>
        <p class="red">${message}</p>
        <p class="grey"> ${sentence} </p>
        <p>Suche nach dem Autor. Vielleicht gibt es den Autor schon in meiner Bibliothek. </p>

        <p>
        <b>Titel:</b> ${titel}, <b>ISBN:</b> ${isbn}, <b>Verlag:</b> ${publisher}, <b>Erscheinungsjahr:</b> ${year}.
        </p>
        <form class="form" name="search-author" action= "${pageContext.request.contextPath}/added-author-after-book/" method="POST">
            <table class="form-table"border="0">
                    <tr>
                        <td>Name: </td>
                        <td><input class="text-field" type="text" name="name" value="" size="50" /> </td>
                    </tr>
            </table>
            <p class="grey"><i>
                Du kannst jeweils nach dem vollen Namen, nach Vornamen oder nach Nachnamen suchen </i></p>
            <input type="hidden" name="titel" value="${titel}">
            <input type="hidden" name="isbn" value="${isbn}">
            <input type="hidden" name="publisher" value="${publisher}">
            <input type="hidden" name="year" value="${year}">
            <input class="button" type="reset" value="Feld leeren" name="clear" />
            <input class="button" type="submit" value="Suchen" name="submit" />
        </form>
    </div>

</main>

</body>
</html>