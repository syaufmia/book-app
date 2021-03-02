<%--
  Created by IntelliJ IDEA.
  User: uzuns
  Date: 17.02.2021
  Time: 11:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="head-navbar.jsp" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<body>
<main>
    <div class="text-box">
        <h1>Welchen Autor möchtest du löschen?</h1>
        <p class="red">${requestScope.message}</p>
        <p class="grey"> ${requestScope.sentence} </p>
        <p>Gib ein Suchbegriff ein, nach dem du Bücher suchen möchtest.</p>

        <p>
        </p>
        <form class="form" name="search-book" action= "${pageContext.request.contextPath}/deleted-book/" method="POST">
            <table class="form-table">
                <tr>
                    <td>Suchbegriff: </td>
                    <td><label>
                        <input class="text-field" type="text" name="word" value="" size="50" />
                    </label></td>
                </tr>
            </table>
            <p class="grey"><i>
                Du kannst in diesem Bereich nach ISBN, Autor, Titel oder Verlag suchen.</i></p>

            <input class="button" type="reset" value="Feld leeren" name="clear" />
            <input class="button" type="submit" value="Suchen" name="submit" />
        </form>
    </div>

</main>

</body>
</html>
