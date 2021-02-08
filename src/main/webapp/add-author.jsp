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
        <h1>Füge einen neuen Autor hinzu</h1>
        <p>Bitte fülle alle notwendigen Felder aus.</p>
        <p class="red">${message}</p>
        <p class="grey">
        <b> ${firstName} ${lastName} </b> ${sentence}
        </p>
        <%-- actionURl => URL depending on the outcome of the addAuthorServlet --%>
        <form class="form" name="add-author" action="${actionURL}" method="POST">
            <table class="form-table"border="0">
                    <tr>
                        <td>Vorname: </td>
                        <td><input class="text-field" type="text" name="firstName" value="" size="50" /> </td>
                    </tr>
                    <tr>
                        <td>Nachname: </td>
                        <td><input class="text-field" type="text" name="lastName" value="" size="50" /> </td>
                    </tr>

            </table>
            <input type="hidden" name="titel" value="${requestScope.titel}">
            <input type="hidden" name="isbn" value="${requestScope.isbn}">
            <input type="hidden" name="publisher" value="${requestScope.publisher}">
            <input type="hidden" name="year" value="${requestScope.year}">
            <input class="button" type="reset" value="Felder leeren" name="clear" />
            <input class="button" type="submit" value="Hinzufügen" name="submit" />
        </form>
        </c:when>
            <c:otherwise>
                <jsp:forward page="login-page.jsp"/>
            </c:otherwise>
        </c:choose>
    </div>

</main>

</body>
</html>