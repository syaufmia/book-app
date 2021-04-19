<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="head-navbar.jsp" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
    <body>
        <main>
            <div class="text-box">
                <h1>Wer ist der Autor des Buches?</h1>
                <p>Wähle eine Option.</p>
                <p class="red">${requestScope.message}</p>
                <p class="grey"> ${requestScope.sentence} </p>
                <form class="form" name="select-author" action= "${pageContext.request.contextPath}/added-book/" method="POST">
                    <table class="form-table">
                        <c:forEach var="author" items="${requestScope.filteredAuthorList}">
                            <tr>
                                <td>
                                    <label>
                                    <input type="radio" name="authorId" value="${author.authorId}" size="100" checked="checked"/>
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        ${author.toStringNoId()}
                                    </label>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <p class="grey">
                        Wähle einen Autor aus oder gib einen neuen Autor ein.
                    </p>
                    <input type="hidden" name="titel" value="${requestScope.titel}">
                    <input type="hidden" name="isbn" value="${requestScope.isbn}">
                    <input type="hidden" name="publisher" value="${requestScope.publisher}">
                    <input type="hidden" name="year" value="${requestScope.year}">
                    <input type="hidden" name="name" value="${requestScope.name}">
                    <input class="darker-button" type="submit" value="Autor auswählen" name="submit"/>
                </form>
                <form name="add-author" action="${pageContext.request.contextPath}/added-author/" method="POST">
                <input type="hidden" name="titel" value="${requestScope.titel}">
                <input type="hidden" name="isbn" value="${requestScope.isbn}">
                <input type="hidden" name="publisher" value="${requestScope.publisher}">
                <input type="hidden" name="year" value="${requestScope.year}">
                <input class="button" type="submit" value="Anderer Autor" name="submit"/>
                </form>
            </div>
        </main>
    </body>
</html>
