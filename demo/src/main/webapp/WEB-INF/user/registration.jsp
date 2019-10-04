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

    <script type = "text/javascript">

             function validateForm() {
                 var password = document.forms["registrationForm"]["encryptedPassword"].value;
                 var retype = document.forms["registrationForm"]["retypePassword"].value;
                 if(password != retype) {
                     alert("Password and retype password must be the same!");
                     return false;
                 }
                 return true;
             }
          </script>
</head>
<body>
<div class="container">

    <h2 class="text-center">Register account</h2>
    <div>
        <form name="registrationForm" class="form-horizontal" th:action="@{registration}" method="post" onsubmit = "return validateForm()">

            <div class="form-group d-flex justify-content-center">
                <label class="col-sm-1 control-label">Username:</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control" name="userName" required/>
                </div>
            </div>
            <div class="form-group d-flex justify-content-center">
                <label class="col-sm-1 control-label">Password:</label>
                <div class="col-sm-6">
                    <input type="password" class="form-control" name="encryptedPassword" required/>
                </div>
            </div>
            <div class="form-group d-flex justify-content-center">
                <label class="col-sm-1 control-label">Retype Password:</label>
                <div class="col-sm-6">
                    <input type="password" class="form-control" name="retypePassword" required/>
                </div>
                        </div>
            <div class="text-center">
                <button type="submit" class="btn btn-info">Submit</button>
            </div>
        </form>
        <div class="text-center">
            Already have an account? Let <a href="login">sign in</a>
        </div>
    </div>
</div>

</body>
</html>