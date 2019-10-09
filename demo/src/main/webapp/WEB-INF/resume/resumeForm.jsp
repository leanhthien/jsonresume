<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Spring Core Online Tutorial - Product Form</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

    <link href="../static/css/spring-core.css"
          th:href="@{css/spring-core.css}" rel="stylesheet" media="screen"/>
</head>
<body>

<jsp:include page="../_nav.jsp"></jsp:include>

<div class="container">

    <h2 class ="text-center">Resume Details</h2>
    <div>
        <c:set var="context" value="${pageContext.request.contextPath}" />
        <form class="form-horizontal" action="${context}/servlet/product/new" method="post">

            <input type="hidden" name="productId" value="${product.productId}"/>
            <div class="form-group">
                    <label class="col-sm-2 control-label">Name:</label>
                    <div class="col">
                        <input type="text" class="form-control" name="name" value="${product.name}"/>
                    </div>
            </div>
            <div class="form-group">
                    <label class="col-sm-2 control-label">Job Title:</label>
                    <div class="col">
                        <input type="text" class="form-control" name="jobTitle" value="${product.jobTitle}"/>
                    </div>
            </div>   
            <div class="form-group">
                    <label class="col-sm-2 control-label">Address:</label>
                    <div class="col">
                        <input type="text" class="form-control" name="address" value="${product.address}"/>
                    </div>
            </div>
            <div class="form-group">
                    <label class="col-sm-2 control-label">Telephone:</label>
                    <div class="col">
                        <input type="text" class="form-control" name="telephone" value="${product.telephone}"/>
                    </div>
            </div>
            <div class="form-group">
                    <label class="col-sm-2 control-label">Email:</label>
                    <div class="col">
                        <input type="text" class="form-control" name="email" value="${product.email}"/>
                    </div>
            </div>
            <div class="form-group">
                    <label class="col-sm-2 control-label">Website:</label>
                    <div class="col">
                        <input type="text" class="form-control" name="website" value="${product.website}"/>
                    </div>
            </div>
            <div class="form-group">
                    <label class="col-sm-2 control-label">Language:</label>
                    <div class="col">
                        <input type="text" class="form-control" name="language" value="${product.language}"/>
                    </div>
            </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">About:</label>
                    <div class="col">
                        <textarea class="form-control" rows="3" name="about" value="${product.about}"></textarea>
                    </div>
            </div>

            <div class="text-center">
                <button type="submit" class="btn btn-info">Submit</button>
                <a href="/" class="btn btn-danger" role="button">Cancel</a>
            </div>
        </form>
    </div>

    <div style="height:75px;"></div>

</div>

</body>
</html>