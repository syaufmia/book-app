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
                <p class="red">${requestScope.message}</p>
                <p class="grey"> ${requestScope.sentence} </p>
                <form class="form" name="select-author" action= "${pageContext.request.contextPath}/deleted-author/" method="POST">
                    <table class="form-table">
                        <c:forEach var= "author" items="${requestScope.authorSearchList}">
                            <tr>
                                <td>
                                    <label>
                                        <input type="radio" name="selected" value="${author.authorId}" size="100" checked="checked" />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        ${author.firstName}
                                        ${author.lastName}
                                    </label>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <p class="grey">
                        Wähle einen Autor aus oder gib einen neuen Autor ein.
                    </p>
                    <input class="darker-button" type="submit" value="Autor auswählen" name="submit"/>
                </form>
                <form name="return" action="index.jsp" method="POST">
                    <input class="button" type="submit" value="Zurück zu Anfang" name="submit"/>
                </form>
            </div>
        </main>
    </body>
</html>
