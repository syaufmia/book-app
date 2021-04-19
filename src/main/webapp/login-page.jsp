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
                    <c:when test="${sessionScope.user != null}">

                        <p class="grey"> Hallo ${sessionScope.user.firstName} ${sessionScope.user.lastName}. </p>
                        <h1>Ausgeliehene Bücher</h1>
                        <table class="real-table">
                            <tr>
                                <th>Buchtitel</th>
                                <th>Isbn</th>
                                <th>ausgeliehen bis</th>
                                <th>zurückgeben</th>
                                <th>verlängern</th>
                            </tr>
                            <form action="${pageContext.request.contextPath}/login/" method="post">
                            <c:forEach var= "loan" items="${sessionScope.loanList}">
                                <tr>
                                    <td>${loan.book.title}</td>
                                    <td>${loan.book.isbn}</td>
                                    <td>${loan.endDate}</td>
                                    <td><button class="table-button" name="loanIdReturn" type="submit" value="${loan.loanId}">JA</button></td>
                                    <td><button class="table-button" name="loanIdExtend" type="submit" value="${loan.loanId}">JA</button></td>
                                </tr>
                            </c:forEach>
                            </form>
                        </table>
                        <form name="logout-form" action="${pageContext.request.contextPath}/login/" method="POST">
                            <input type="hidden" name="logout" value="true">
                            <input class="button" type="submit" value="ausloggen" name="submit" />
                        </form>
                </c:when>
                <c:otherwise>
                    <div class="text-box">
                    <h1>Melde dich an</h1>
                    <p>Bitte gib deinen Benutzernamen und Passwort ein.</p>
                    <p class="red">${requestScope.message}</p>
                    <p class="grey"> ${requestScope.sentence} </p>
                    <%-- actionURl => URL depending on the outcome of the addAuthorServlet --%>

                    <form class="form" name="login" action="${pageContext.request.contextPath}/login/" method="POST">
                        <table class="form-table">
                            <tr>
                                <td>Benutzername: </td>
                                <td>
                                    <label>
                                    <input class="text-field" type="text" name="username" value="" size="50" />
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <td>Passwort: </td>
                                <td>
                                    <label>
                                    <input class="text-field" type="password" name="password" value="" size="50" />
                                    </label>
                                </td>
                            </tr>
                        </table>
                        <input class="button" type="reset" value="Felder leeren" name="clear" />
                        <input class="button" type="submit" value="Anmelden" name="submit" />
                    </form>
                    </div>
                </c:otherwise>
                </c:choose>
            </div>
        </main>
    </body>
</html>
