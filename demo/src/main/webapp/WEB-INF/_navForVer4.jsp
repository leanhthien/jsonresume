<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div style="padding-bottom: 50px;">

    <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">

        <a class="navbar-brand" href="${pageContext.request.contextPath}/servlet/home">Json Resume</a>

        <ul class="navbar-nav">
            <li class="nav-item">
                <c:if test="${empty sessionScope.loginUser}">
                    <a class="nav-link" href="${pageContext.request.contextPath}/servlet/registration">Sign up</a>
                </c:if>
            </li>
            <li class="nav-item">
                <c:if test="${empty sessionScope.loginUser}">
                    <a class="nav-link" href="${pageContext.request.contextPath}/servlet/login">Sign in</a>
                </c:if>
            </li>
            <li class="nav-item">
                <c:if test="${!empty sessionScope.loginUser}">
                    <a class="nav-link" href="${pageContext.request.contextPath}/servlet/product/all">Themes</a>
                </c:if>
            </li>
            <li class="nav-item">
                <c:if test="${!empty sessionScope.loginUser}">
                    <a class="nav-link" href="${pageContext.request.contextPath}/servlet/product/new">Create resume</a>
                </c:if>
            </li>
            <li class="nav-item">
                <c:if test="${!empty sessionScope.loginUser}">
                    <a class="nav-link" href="${pageContext.request.contextPath}/servlet/product/user">
                        <c:out value="${sessionScope.loginUser}" />
                    </a>
                </c:if>
            </li>
            <li class="nav-item">
                <c:if test="${!empty sessionScope.loginUser}">
                    <a class="nav-link" href="${pageContext.request.contextPath}/servlet/logout">Log out</a>
                </c:if>
            </li>

        </ul>
    </nav>

</div>


