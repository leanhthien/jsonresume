<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <title>List Products</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/v4-shims.css">

    <link href="../../../resources/static/css/spring-core.css"
          href="@{css/spring-core.css}" rel="stylesheet" media="screen"/>
</head>
<body>

<div class="container">

   <div class="row">
      <div class="col-3">
          <span class="profile-pic-container">
              <div class="profile-pic">
                <img class="media-object img-circle center-block" data-src="holder.js/100x100" alt="Richard Hendriks"
                     src="https://s.gravatar.com/avatar/7e6be1e623fb85adde3462fa8587caf2?s=100&amp;r=pg&amp;d=mm"
                     itemprop="image"/>
              </div>
              <div class="name-and-profession">
                <h3><c:if test="${!empty sessionScope.loginUser}"><c:out value="${sessionScope.loginUser}" /></c:if></h3>
              </div>
          </span>

        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
          Share your resume
        </button>

      </div>
   </div>

   <!-- The Modal -->
   <div class="modal" id="myModal">
     <div class="modal-dialog">
       <div class="modal-content">

         <!-- Modal body -->
         <div class="modal-body">

           Share this link to another for showing your resume:
            <c:set var="context" value="${pageContext.request.contextPath}" />
            <input type="text" class="form-control" value="<c:if test='${!empty sessionScope.loginUser}'>${context}/servlet/view?name=${sessionScope.loginUser}</c:if>"/>
            <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
         </div>

       </div>
     </div>
   </div>

    <c:if test="${!empty errorResponse}">
        <div class="alert alert-danger" role="alert">
             <c:out value="${errorResponse}" />
        </div>
    </c:if>

    <c:if test="${!empty successResponse}">
        <div class="alert alert-primary" role="alert">
             <c:out value="${successResponse}" />
        </div>
    </c:if>

    <c:if test="${empty products}">
    <div class="row">
        <p>You don't have any resume. Let make new one to start!</p>
    </div>
    </c:if>

    <c:if test="${!empty products}">
    <div>
        <h2>Your Resume List</h2>
        <table class="table table-striped">
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Job Title</th>
                <th>Top</th>
                <th colspan="4">Action</th>
            </tr>
            <c:forEach items="${products}" var="product" >
            <tr>
                <td>${product.productId}</td>
                <td>${product.name}</td>
                <td>${product.jobTitle}</td>
                <td>
                <c:if test="${product.enabled == true}">
                        <a href="#"><i class="fas fa-star"></i></a>
                </c:if>
                </td>
                <td><a href="detail?id=<c:out value='${product.productId}'/>">View</a></td>
                <td><a href="edit?id=<c:out value='${product.productId}'/>">Edit</a></td>
                <td>
                <c:if test="${product.enabled == false}">
                    <a href="enable?id=<c:out value='${product.productId}'/>">Enable Top</a>
                </c:if>
                </td>

                <td><a href="delete?id=<c:out value='${product.productId}'/>">Delete</a></td>
            </tr>
            </c:forEach>
        </table>
    </div>
    </c:if>

    <div class="text-center">
         <a href="new" class="btn btn-info" role="button">New Resume</a>
    </div>
</div>

</body>
</html>