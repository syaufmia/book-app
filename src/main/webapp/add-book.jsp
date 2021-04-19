<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="head-navbar.jsp" %>


<!DOCTYPE html>
<html lang="en">
    <body>
        <main>
            <div class="text-box">
                <h1>Füge ein neues Buch hinzu</h1>
                <p class="red">${requestScope.message}</p>
                <p class="grey"> ${requestScope.sentence} </p>
                <p>Bitte fülle alle notwendigen Felder aus.</p>
                <form class="form" name="add-book" action="${requestScope.actionURL}" method="POST">
                    <table class="form-table">
                            <tr>
                                <td>Titel: </td>
                                <td>
                                    <label>
                                        <input class="text-field" type="text" name="titel" value=""/>
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <td>ISBN: </td>
                                <td>
                                    <label>
                                        <input class="text-field" type="text" name="isbn" value=""/>
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <td>Verlag: </td>
                                <td>
                                    <label>
                                        <input class="text-field" type="text" name="publisher" value=""/>
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <td>Erscheinungsjahr: </td>
                                <td>
                                    <label>
                                        <input class="text-field" type="number" name="year" min="1950" max="2020" step="1" value="2020"/>
                                    </label>
                                </td>
                            </tr>
                    </table>
                    <input class="darker-button" type="submit" value="Weiter zum Autor" name="submit"/>
                    <input class="button" type="reset" value="Felder leeren" name="clear"/>
                </form>
            </div>
        </main>
    </body>
</html>
