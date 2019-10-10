<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Spring Core Online Tutorial - Product Form</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <link href="http://cdn.jsdelivr.net/webjars/bootstrap/4.3.1/css/bootstrap.min.css"
          href="@{/webjars/bootstrap/4.3.1/css/bootstrap.min.css}"
          rel="stylesheet" media="screen"/>

    <script src="http://cdn.jsdelivr.net/webjars/jquery/3.4.1/jquery.min.js"
            src="@{/webjars/jquery/3.4.1/jquery.min.js}"></script>

    <link href="../static/css/spring-core.css"
          href="@{css/spring-core.css}" rel="stylesheet" media="screen"/>
</head>
<body>

<jsp:include page="_nav.jsp"></jsp:include>

<div class="container">

    <div style="height: 75px;"></div>

    <h2 class="text-center">Page not found</h2>
    <p class="text-center">The page you looking for is not exist</p>

</div>

</body>
</html>