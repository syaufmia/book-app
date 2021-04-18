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

    <c:choose>
    <c:when test="${sessionScope.user != null}">
        <div class="text-box">
            <h1> ${tableName} </h1>
            <p class="grey"> ${requestScope.sentence} </p>
            <p class="red"> ${requestScope.message} </p>

            <div class="text-box">
                <table class="real-table">

                    <tr>
                        <th>
                            title
                        </th>
                        <th>
                            isbn
                        </th>
                        <th>
                            borrow
                        </th>
                    </tr>

                    <c:forEach var="book" items="${requestScope.bookList}">
                        <tr>
                            <td>
                                    ${book.title}
                            </td>
                            <td>
                                    ${book.isbn}
                            </td>
                        <c:choose>
                            <c:when test="${requestScope.borrowedBookList.contains(book)}">
                                <td>
                                    BOOKED
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td>
                                    available
                                </td>
                            </c:otherwise>
                        </c:choose>
                        </tr>

                    </c:forEach>

                </table>
            </div>

        </div>
    </c:when>
        <c:otherwise>
            <div class="text-box">
                <h1> ${tableName} </h1>
                <p class="grey"> ${requestScope.sentence} </p>
                <p class="red"> ${requestScope.message} </p>

                <div class="text-box">
                    <table class="real-table">

                        <tr>
                            <th>
                                title
                            </th>
                            <th>
                                isbn
                            </th>
                        </tr>

                        <c:forEach var="book" items="${requestScope.bookList}">
                            <tr>
                                <td>
                                        ${book.title}
                                </td>
                                <td>
                                        ${book.isbn}
                                </td>
                            </tr>

                        </c:forEach>

                    </table>
                </div>

            </div>

        </c:otherwise>
    </c:choose>

</main>

</body>
</html>
