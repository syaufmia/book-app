<%--
  Created by IntelliJ IDEA.
  User: uzuns
  Date: 29.04.2021
  Time: 22:30
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
                <h1>Registriere ein neues Benutzerkonto</h1>
                <p>Bitte fülle alle Felder aus.</p>
                <p class="red">${requestScope.message}</p>
                <p class="grey"> ${requestScope.sentence} </p>
                <form class="form" name="register" action="${pageContext.request.contextPath}/register/" method="POST">
                    <table class="form-table">
                        <tr>
                            <td>Benutzername: </td>
                            <td>
                                <label>
                                    <input class="text-field" type="text" name="username" value=""/>
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td>E-Mail: </td>
                            <td>
                                <label>
                                    <input class="text-field" type="email" name="email" value=""/>
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td>Vorname: </td>
                            <td>
                                <label>
                                    <input class="text-field" type="text" name="firstName" value=""/>
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td>Nachname: </td>
                            <td>
                                <label>
                                    <input class="text-field" type="text" name="lastName" value=""/>
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td>Passwort: </td>
                            <td>
                                <label>
                                    <input class="text-field" type="password" name="password" value=""/>
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td>Passwort bestätigen: </td>
                            <td>
                                <label>
                                    <input class="text-field" type="password" name="password2" value=""/>
                                </label>
                            </td>
                        </tr>
                    </table>
                    <input class="darker-button" type="submit" value="Registrieren" name="submit"/>
                    <input class="button" type="reset" value="Felder leeren" name="reset"/>
                </form>
            </div>
        </main>
    </body>
</html>
