<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container-fluid">
<nav class="navbar navbar-default">
        <div class="navbar-header">
            <a class="navbar-brand" href="/">Json Resume</a>
        </div>
        <ul class="nav navbar-nav navbar-right">

            <li >
                      <c:if test="${empty sessionScope.loginUser}">
                          <a href="${pageContext.request.contextPath}/servlet/registration">Sign up</a>
                      </c:if>
                      </li>
                      <li >
                      <c:if test="${empty sessionScope.loginUser}">
                          <a href="${pageContext.request.contextPath}/servlet/login">Sign in</a>
                      </c:if>
                      </li>
                      <li >
                      <c:if test="${!empty sessionScope.loginUser}">
                          <a href="${pageContext.request.contextPath}/servlet/product/all">Themes</a>
                      </c:if>
                      </li>
                      <li >
                      <c:if test="${!empty sessionScope.loginUser}">
                          <a href="${pageContext.request.contextPath}/servlet/product/new">Create resume</a>
                      </c:if>
                      </li>
                      <li >
                      <c:if test="${!empty sessionScope.loginUser}">
                          <a href="${pageContext.request.contextPath}/servlet/product/user"><c:out value="${sessionScope.loginUser}"/></a>
                      </c:if>
                      </li>
                      <li >
                      <c:if test="${!empty sessionScope.loginUser}">
                          <a href="${pageContext.request.contextPath}/servlet/logout">Log out</a>
                      </c:if>
                      </li>

        </ul>
</nav>
</div>