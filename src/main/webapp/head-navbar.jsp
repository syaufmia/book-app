
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css" />
</head>

<nav class="menu">
    <header>
        <h2>Safi's Bibliothek</h2>
        <img src="${pageContext.request.contextPath}/style/img/openbook.png" alt="open book"/>
    </header>
    <ul class="main-menu">
        <li><span>Listen anzeigen</span>
            <ul class="sub-menu">
                <li class="with-link"><a href="${pageContext.request.contextPath}/showed-alist/">Alle Autoren</a></li>
                <li class="with-link"><a href="${pageContext.request.contextPath}/showed-blist/">Alle Bücher</a></li>
            </ul>
        </li>
        <li><span>Neu hinzufügen</span>
            <ul class="sub-menu">
                <li class="with-link"><a href="${pageContext.request.contextPath}/added-book/">Buch hinzufügen</a></li>
                <li class="with-link"><a href="${pageContext.request.contextPath}/added-author/">Autor hinzufügen</a></li>
            </ul>
        </li>
        <li><span>Löschen</span>
            <ul class="sub-menu">
                <li class="with-link"><a href="${pageContext.request.contextPath}/deleted-book/">Buch löschen</a></li>
                <li class="with-link"><a href="${pageContext.request.contextPath}/deleted-author/">Autor löschen</a></li>
            </ul>
        </li>
        <li><span>Sonstiges</span></li>
        <li class="with-link"><a href="${pageContext.request.contextPath}/login/">My page</a>
        </li>
    </ul>

</nav>
