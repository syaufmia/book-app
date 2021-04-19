<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="head-navbar.jsp" %>


<!DOCTYPE html>
<html lang="en">
    <body>
        <main>
            <div class="text-box">
            <p class="red">${requestScope.message}</p>
            <p class="grey"> ${requestScope.sentence} </p>
                <h1>Willkommen in meiner Bibliothek.</h1>
                <span>In dieser Applikation sind alle Informationen über die Bücher in meiner Bibliothek zu finden.</span>
                <span>Im Menü kannst du neue Bücher oder Autoren hinzufügen, bereits vorhandene wieder löschen und mehr.</span>
            </div>
        </main>
    </body>
</html>
