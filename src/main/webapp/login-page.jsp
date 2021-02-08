<%--
  Created by IntelliJ IDEA.
  User: uzuns
  Date: 01.02.2021
  Time: 15:58
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
        <c:choose>
            <c:when test="${sessionScope.loggedIn == true}">
            <div class="text-box">
                <p class="red">${message}</p>
                <p class="grey"> ${sentence} </p>
                <h1>Willkommen in meiner Bibliothek.</h1>
                <span>In dieser Applikation sind alle Informationen über die Bücher in meiner Bibliothek zu finden.</span>
                <span>Im Menü kannst du neue Bücher oder Autoren hinzufügen, bereits vorhandene wieder löschen und mehr.</span>
            </div>
        </c:when>
        <c:otherwise>
            <div class="text-box">
            <h1>Melde dich an</h1>
            <p>Bitte gib deinen Benutzernamen und Passwort ein.</p>
            <p class="red">${message}</p>
            <p class="grey"> ${sentence} </p>
            <%-- actionURl => URL depending on the outcome of the addAuthorServlet --%>
            <form class="form" name="login" action="login" method="POST">
                <table class="form-table"border="0">
                    <tr>
                        <td>Benutzername: </td>
                        <td><input class="text-field" type="text" name="username" value="" size="50" /> </td>
                    </tr>
                    <tr>
                        <td>Passwort: </td>
                        <td><input class="text-field" type="password" name="password" value="" size="50" /> </td>
                    </tr>

                </table>
                <input class="button" type="reset" value="Felder leeren" name="clear" />
                <input class="button" type="submit" value="Hinzufügen" name="submit" />
            </form>
            </div>
        </c:otherwise>
        </c:choose>
    </div>

</main>

</body>
</html>
