<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Product List</title>
  </head>
  <body>

     <h3>Resume List</h3>

     <table border="1" cellpadding="5" cellspacing="1" >
        <tr>
           <th>Author</th>
           <th>Name</th>
           <th>Job Detail</th>
           <th>Action</th>
        </tr>
        <c:forEach items="${products}" var="product" >
        <tr>
            <td>${product.appUser.userName}</td>
            <td>${product.name}</td>
            <td>${product.jobTitle}</td>
            <td>
               <a href="detail?id=<c:out value='${product.productId}' />">View</a>
            </td>
        </tr>
           </c:forEach>
     </table>
  </body>
</html>