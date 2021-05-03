<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:default-layout title="Chat App - Bejelentkezés">
    <div class="card" style="width: 600px">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/LoginController" method="post" class="login-form">
                <h4>Chat App - Bejelentkezés</h4>
                <c:if test="${requestScope.error != null}">
                    <c:if test="${requestScope.error == 'Sikeres regisztráció!'}">
                        <div class="alert alert-success" role="alert" style="width: 100%">
                                ${requestScope.error}
                        </div>
                    </c:if>
                    <c:if test="${requestScope.error != 'Sikeres regisztráció!'}">
                        <div class="alert alert-danger" role="alert" style="width: 100%">
                                ${requestScope.error}
                        </div>
                    </c:if>
                </c:if>
                <div class="form-group">
                    <input class="form-control" name="nickname" type="text" placeholder="Felhasználónév" required>
                    <input class="form-control" name="password" type="password" placeholder="Jelszó" required>
                    <div class="flex-row">
                        <button class="btn btn-primary" type="submit">Bejelentkezés</button>
                        <a href="${pageContext.request.contextPath}/pages/register.jsp" class="btn btn-success">Regisztráció</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</t:default-layout>
