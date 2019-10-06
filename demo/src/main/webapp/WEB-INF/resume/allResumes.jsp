<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Spring Core Online Tutorial - List Products</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

    <link href="../static/css/spring-core.css"
          th:href="@{css/spring-core.css}" rel="stylesheet" media="screen"/>
</head>
<body>
<div class="container">

    <c:if test="${!empty errorResponse}">
            <div class="alert alert-danger" role="alert">
                 <c:out value="${errorResponse}" />
            </div>
    </c:if>

    <c:if test="${empty products}">
    <div class="row">
        <p>You don't have any resume. Let make new one to start!</p>
    </div>
    </c:if>

    <div >
        <div class="px-3" >
            <h2>All Resume List</h2>
        </div>

        <c:if test="${!empty products}">
        <table class="table table-striped">
            <tr>
                <th>Author</th>
                <th>Name</th>
                <th>Job Title</th>
                <th colspan="4">Action</th>
            </tr>
            <c:forEach items="${products}" var="product" >
                <tr>
                    <td>${product.appUser.userName}</td>
                    <td>${product.name}</td>
                    <td>${product.jobTitle}</td>
                    <td>
                        <a href="detail?id=<c:out value='${product.productId}'/>">View</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        </c:if>

    </div>
    <div class="row">
        <div class="text-center">
            <a href="new" class="btn btn-info" role="button">New Resume</a>
        </div>
    </div>
</div>

</body>
</html>