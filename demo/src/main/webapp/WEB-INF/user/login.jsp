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
<div class="container">

    <div style="height: 75px;"></div>

    <h2 class="text-center">Log in</h2>

    <div style="height: 10px;"></div>

    <c:if test="${!empty errorResponse}">
         <div class="alert alert-danger" role="alert">
              <c:out value = "${errorResponse}" />
         </div>
    </c:if>

    <div>
        <c:set var="context" value="${pageContext.request.contextPath}" />
        <form class="form-horizontal" action="${context}/servlet/login" method="post">
            <div class="form-group d-flex justify-content-center">
                <label class="col-sm-1 control-label" >Username:</label>
                <div class="col-sm-6">
                    <input type="text" name='username' class="form-control"/>
                </div>
            </div>
            <div class="form-group d-flex justify-content-center">
                <label class="col-sm-1 control-label" >Password:</label>
                <div class="col-sm-6">
                    <input type="password" name='password' class="form-control"/>
                </div>
            </div>
            <div style="height: 10px;"></div>

            <div class="text-center">
                <button type="submit" class="btn btn-info">Log in</button>
            </div>
        </form>

        <div style="height: 20px;"></div>
        <div class="text-center">
            Don't have an account? Let <a href="registration">sign up</a>
        </div>

    </di>
</div>

</body>
</html>