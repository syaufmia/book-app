<%--
  Created by IntelliJ IDEA.
  User: uzuns
  Date: 18.04.2021
  Time: 13:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="head-navbar.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <body>
        <main>
            <div class="text-box">
                <h1>Bücherliste</h1>
                <p class="grey">${requestScope.sentence}</p>
                <p class="red">${requestScope.message}</p>
                <div class="text-box">
                    <table class="real-table">
                        <tr>
                            <th>Titel</th>
                            <th>Isbn</th>
                            <th>Autor</th>
                            <th>Verlag</th>
                            <th>Jahr</th>
                            <c:choose>
                                <c:when test="${sessionScope.user != null}">
                                <th>verfügbar</th>
                                </c:when>
                            </c:choose>
                        </tr>
                        <c:forEach var="book" items="${requestScope.bookList}">
                            <tr>
                                <td>${book.title}</td>
                                <td>${book.isbn}</td>
                                <td>${book.author.lastName}</td>
                                <td>${book.publisher}</td>
                                <td>${book.publishedYear}</td>
                                <c:choose>
                                    <c:when test="${sessionScope.user != null}">
                                        <c:choose>
                                            <c:when test="${requestScope.borrowedBookList.contains(book)}">
                                                <td>NEIN</td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>JA</td>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                </c:choose>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </main>
    </body>
</html>
