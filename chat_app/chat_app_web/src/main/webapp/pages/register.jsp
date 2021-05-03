<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:default-layout title="Chat App - Regisztráció">
    <div class="card" style="width: 600px">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/RegisterController" method="post" class="login-form">
                <h4>Chat App - Regisztráció</h4>
                <c:if test="${requestScope.error != null}">
                    <div class="alert alert-danger" role="alert" style="width: 100%">
                        ${requestScope.error}
                    </div>
                </c:if>
                <div class="form-group">
                    <input class="form-control" name="nickname" type="text" placeholder="Felhasználónév" required>
                    <input class="form-control" name="password" type="password" placeholder="Jelszó" required>
                    <input class="form-control" name="passwordAgain" type="password" placeholder="Jelszó mégegyszer" required>
                    <input class="form-control" name="age" type="number" placeholder="Életkor">
                    <input class="form-control" name="interest" type="text" placeholder="Érdeklődési kör">
                    <div class="flex-row p-2 h-15">
                        <div class="register-genderclass rounded">
                            <input type="radio" id="male" name="gender" value="MALE">
                            <label for="male">Férfi</label><br>
                        </div>
                        <div class="register-genderclass rounded" >
                            <input type="radio" id="female" name="gender" value="FEMALE">
                            <label for="female">Nő</label><br>
                        </div>
                        <div class="register-genderclass rounded">
                            <input type="radio" id="other" name="gender" value="OTHER">
                            <label for="other">Egyéb</label>
                        </div>
                        <div class="register-genderclass rounded">
                            <input type="radio" id="unknown" name="gender" value="UNKNOWN" checked>
                            <label for="unknown">Ismeretlen</label><br>
                        </div>
                    </div>
                    <div class="flex-row">
                        <button class="btn btn-primary" type="submit">Regisztráció</button>
                        <a href="${pageContext.request.contextPath}/pages/login.jsp" class="btn btn-danger">Mégse</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</t:default-layout>
