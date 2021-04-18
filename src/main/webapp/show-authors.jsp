<%--
  Created by IntelliJ IDEA.
  User: uzuns
  Date: 18.04.2021
  Time: 22:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="head-navbar.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <body>
        <main>
            <div class="text-box">
                <h1>Autorenliste</h1>
                <p class="grey">${requestScope.sentence}</p>
                <p class="red">${requestScope.message}</p>
                <div class="text-box">
                    <table class="real-table">
                        <tr>
                            <th>Vorname</th>
                            <th>Nachname</th>
                        </tr>
                        <c:forEach var="author" items="${requestScope.authorList}">
                            <tr>
                                <td>${author.firstName}</td>
                                <td>${author.lastName}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </main>
    </body>
</html>
