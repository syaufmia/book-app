<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%@ include file="head-navbar.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">

<body>
<main>

    <div class="text-box">
        <h1> ${tableName} </h1>
        <p class="grey"> ${sentence} </p>
        <p class="red"> ${message} </p>

        <div class="text-box">
        <table class="real-table" border="0">

        <%
        out.println(request.getAttribute("htmlText"));
        %>

        </table>
        </div>

    </div>
</main>

</body>
</html>