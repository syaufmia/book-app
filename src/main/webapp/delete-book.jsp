<%--
  Created by IntelliJ IDEA.
  User: uzuns
  Date: 17.02.2021
  Time: 10:34
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
                <h1>Welches Buch möchtest du löschen?</h1>
                <p>Wähle eine Option.</p>
                <p class="red">${requestScope.message}</p>
                <p class="grey"> ${requestScope.sentence} </p>
                <form class="form" name="select-author" action= "${pageContext.request.contextPath}/deleted-book/" method="POST">
                    <table class="form-table">
                        <c:forEach var= "book" items="${requestScope.searchList}">
                            <tr>
                                <td>
                                    <label>
                                        <input type="radio" name="filtered-book" value="${book.isbn}" size="100" checked="checked" />
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        ${book.title},
                                        ${book.isbn},
                                        ${book.publisher}
                                    </label>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <p class="grey">
                        Wähle ein Buch zum Löschen aus.
                    </p>
                    <input class="darker-button" type="submit" value="Buch auswählen" name="submit"/>
                </form>
                <form name="return" action="index.jsp" method="POST">
                    <input class="button" type="submit" value="Zurück zu Anfang" name="submit"/>
                </form>
            </div>
        </main>
    </body>
</html>
