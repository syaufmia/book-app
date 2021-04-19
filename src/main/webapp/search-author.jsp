<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="head-navbar.jsp" %>


<!DOCTYPE html>
<html lang="en">
    <body>
        <main>
            <div class="text-box">
                <h1>Welchen Autor möchtest du löschen?</h1>
                <p class="red">${requestScope.message}</p>
                <p class="grey"> ${requestScope.sentence} </p>
                <p>Suche nach dem Autor, den du aus meiner Bibliothek entfernen möchtest. </p>
                <br>
                <form class="form" name="search-author" action= "${pageContext.request.contextPath}/deleted-author/" method="POST">
                    <table class="form-table">
                            <tr>
                                <td>Name: </td>
                                <td><label>
                                    <input class="text-field" type="text" name="name" value=""/>
                                </label></td>
                            </tr>
                    </table>
                    <p class="grey">
                        <i>Du kannst jeweils nach dem vollen Namen, nach Vornamen oder nach Nachnamen suchen </i>
                    </p>
                    <input class="darker-button" type="submit" value="Suchen" name="submit"/>
                    <input class="button" type="reset" value="Feld leeren" name="clear"/>
                </form>
            </div>
        </main>
    </body>
</html>
