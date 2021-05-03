<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:default-layout title="Chat App - Menü">
    <div class="col-md-12 flex-row">
        <div class="col-md-12">
            <div class="card rooms-card p-2 mb-4">
                <form action="${pageContext.request.contextPath}/SearchController" class="flex-row mb-1" method="post">
                    <input type="search" name="search" class="form-control col-md-5" placeholder="Keresés..." autocomplete="off" >
                    <div class="col-md-5 flex-row">
                        <label class="mb-1 mr-1" for="roomName">Szobanév</label>
                        <input type="radio" class="mr-2" id="roomName" name="searchCategory" value="roomName" checked>
                        <label class="mb-1 mr-1" for="category">Kategória</label>
                        <input type="radio" id="category" name="searchCategory" value="category">
                    </div>
                    <button type="submit" class="btn btn-primary col-md-1"><i class="fas fa-search"></i></button>
                </form>
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">Szoba Neve</th>
                            <th scope="col">Kategória</th>
                            <th scope="col">Gomb</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:if test="${requestScope.chatRoomSearchList != null}">
                        <c:forEach var="room" items="${requestScope.chatRoomSearchList}">
                            <tr>
                                <th scope="row">${room.roomName}</th>
                                <td>${room.category}</td>
                                <td><a href="${pageContext.request.contextPath}/RoomController?roomId=${room.id}" class="btn btn-primary">Csatlakozás</a></td>
                            </tr>
                        </c:forEach>
                    </c:if>

                    <c:if test="${requestScope.chatRoomSearchList == null}">
                        <c:forEach var="room" items="${requestScope.chatRoomList}">
                            <tr>
                                <th scope="row">${room.roomName}</th>
                                <td>${room.category}</td>
                                <td><a href="${pageContext.request.contextPath}/RoomController?roomId=${room.id}" class="btn btn-primary">Csatlakozás</a></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    </tbody>
                </table>
            </div>

            <div class="card users-card p-2">
                <form action="${pageContext.request.contextPath}/SearchController" method="post" class="flex-row mb-1">
                    <input type="search" name="search" class="form-control col-md-5" placeholder="Keresés..." autocomplete="off">
                    <div class="col-md-5 flex-row">
                        <label class="mb-1 mr-1" for="nickname">Név</label>
                        <input type="radio" class="mr-2" id="nickname" name="searchCategory" value="nickname" checked>
                        <label class="mb-1 mr-1" for="interest">Érdeklődési kör</label>
                        <input type="radio" id="interest" name="searchCategory" value="interest">
                    </div>
                    <button type="submit" class="btn btn-primary col-md-1"><i class="fas fa-search"></i></button>
                </form>
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">Felhasználónév</th>
                            <th scope="col">Életkor</th>
                            <th scope="col">Nem</th>
                            <th scope="col">Érdeklődési kör</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:if test="${requestScope.chatUserSearchList != null}">
                        <c:forEach var="user" items="${requestScope.chatUserSearchList}">
                            <tr>
                                <th scope="row">${user.nickname}</th>
                                <td>${user.age}</td>
                                <td>${user.gender.value}</td>
                                <td>${user.interest}</td>
                            </tr>
                        </c:forEach>
                    </c:if>

                    <c:if test="${requestScope.chatUserSearchList == null}">
                        <c:forEach var="user" items="${requestScope.chatUserList}">
                            <tr>
                                <th scope="row">${user.nickname}</th>
                                <td>${user.age}</td>
                                <td>${user.gender.value}</td>
                                <td>${user.interest}</td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="col-md-7 h-100" style="min-width: 300px">
            <div class="card myself-card p-2 h-100">
                <h3>Adatok</h3>
                <ul>
                    <li class="flex-row"><span>Felhasználónév: </span> <p>${sessionScope.currentUser.nickname}</p></li>
                    <li class="flex-row"><span>Életkor: </span> <p>${sessionScope.currentUser.age}</p></li>
                    <li class="flex-row"><span>Neme: </span> <p>${sessionScope.currentUser.gender.value}</p></li>
                    <li class="flex-row"><span>Érdeklődési kör: </span> <p>${sessionScope.currentUser.interest}</p></li>
                </ul>

                <hr>

                <form action="${pageContext.request.contextPath}/LogoutController" method="get">
                    <button type="submit" class="btn btn-danger m-0">Kijelentkezés</button>
                </form>
            </div>
        </div>
    </div>
</t:default-layout>
