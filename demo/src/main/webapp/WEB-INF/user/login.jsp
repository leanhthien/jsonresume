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

    <h2 class="text-center">Log in</h2>

    <!-- /login?error=true -->
    <c:if test ="">
    <div class="alert alert-danger" role="alert">
        Login failed!<br/>
        Reason :
        <span th:if="${#session != null and #session.getAttribute('SPRING_SECURITY_LAST_EXCEPTION') != null}"
              th:utext="${#session.getAttribute('SPRING_SECURITY_LAST_EXCEPTION').message}">
                Static summary
         </span>
    </div>
    </c:if>
    <div>
        <form class="form-horizontal" th:action="@{/login}" method="post">
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
            <div class="form-check text-center">
                <input class="form-check-input" type="checkbox" id="autoSizingCheck" name="remember-me">
                <label class="form-check-label" for="autoSizingCheck">
                    Remember me
                </label>
            </div>

            <div class="text-center">
                <button type="submit" class="btn btn-info">Log in</button>
            </div>
        </form>
        <div class="text-center">
            Don't have an account? Let <a href="/registration/">sign up</a>
        </div>

    </di>
</div>

</body>
</html>