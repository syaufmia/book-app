<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="head-navbar.jsp" %>


<!DOCTYPE html>
<html lang="en">

<body>
<main>
    <div class="text-box">
        <h1>Füge ein neues Buch hinzu</h1>
        <p class="red">${message}</p>
        <p class="grey"> ${sentence} </p>
        <p>Bitte fülle alle notwendigen Felder aus.</p>


        <form class="form" name="add-book" action="${requestScope.actionURL}" method="POST">
            <table class="form-table"border="0">
                    <tr>
                        <td>Titel: </td>
                        <td><input class="text-field" type="text" name="titel" value="" size="50" /> </td>
                    </tr>
                    <tr>
                        <td>ISBN: </td>
                        <td><input class="text-field" type="text" name="isbn" value="" size="50" /> </td>
                    </tr>
                    <tr>
                        <td>Verlag: </td>
                        <td><input class="text-field" type="text" name="publisher" value="" size="50" /> </td>
                    </tr>
                    <tr>
                        <td>Erscheinungsjahr: </td>
                        <td><input class="text-field" type="number" name="year" min="1950" max="2020" step="1" value="2020" size="50" /> </td>
                    </tr>

            </table>
            <input class="button" type="reset" value="Felder leeren" name="clear" />
            <input class="button" type="submit" value="Weiter zum Autor" name="submit" />
        </form>
    </div>
</main>

</body>
</html>