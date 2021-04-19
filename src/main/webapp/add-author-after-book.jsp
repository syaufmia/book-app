<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="head-navbar.jsp" %>

<!DOCTYPE html>
<html lang="en">
    <body>
        <main>
            <div class="text-box">
                <h1>Wer ist der Autor des Buches?</h1>
                <p class="red">${requestScope.message}</p>
                <p class="grey"> ${requestScope.sentence} </p>
                <p>Suche nach dem Autor. Vielleicht gibt es den Autor schon in meiner Bibliothek. </p>
                <p>
                    <b>Titel: </b> ${requestScope.titel},
                    <b>ISBN:</b> ${requestScope.isbn},
                    <b>Verlag:</b> ${requestScope.publisher},
                    <b>Erscheinungsjahr:</b> ${requestScope.year}.
                </p>
                <form class="form" name="search-author" action= "${pageContext.request.contextPath}/added-author-after-book/" method="POST">
                    <table class="form-table">
                            <tr>
                                <td>Name: </td>
                                <td>
                                    <label>
                                        <input class="text-field" type="text" name="name" value=""/>
                                    </label>
                                </td>
                            </tr>
                    </table>
                    <p class="grey">
                        <i>Du kannst jeweils nach dem vollen Namen, nach Vornamen oder nach Nachnamen suchen </i>
                    </p>
                    <input type="hidden" name="titel" value="${requestScope.titel}">
                    <input type="hidden" name="isbn" value="${requestScope.isbn}">
                    <input type="hidden" name="publisher" value="${requestScope.publisher}">
                    <input type="hidden" name="year" value="${requestScope.year}">
                    <input class="darker-button" type="submit" value="Suchen" name="submit"/>
                    <input class="button" type="reset" value="Feld leeren" name="clear"/>
                </form>
            </div>
        </main>
    </body>
</html>
