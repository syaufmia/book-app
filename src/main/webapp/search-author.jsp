<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="head-navbar.jsp" %>


<!DOCTYPE html>
<html lang="en">

<body>
<main>
    <div class="text-box">
        <h1>Welchen Autor möchtest du löschen?</h1>
        <p class="red">${message}</p>
        <p class="grey"> ${sentence} </p>
        <p>Suche nach dem Autor, den du aus meiner Bibliothek entfernen möchtest. </p>

        <p>
        </p>
        <form class="form" name="search-author" action= "${pageContext.request.contextPath}/deleted-author/" method="POST">
            <table class="form-table">
                    <tr>
                        <td>Name: </td>
                        <td><input class="text-field" type="text" name="name" value="" size="50" /> </td>
                    </tr>
            </table>
            <p class="grey"><i>
                Du kannst jeweils nach dem vollen Namen, nach Vornamen oder nach Nachnamen suchen </i></p>

            <input class="button" type="reset" value="Feld leeren" name="clear" />
            <input class="button" type="submit" value="Suchen" name="submit" />
        </form>
    </div>

</main>

</body>
</html>