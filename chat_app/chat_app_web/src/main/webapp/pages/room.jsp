<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:default-layout title="Chat App - ${requestScope.chatRoom.roomName}">
    <c:set var="sorszam" scope="session" value="1"/>
    <div class="col-md-12 flex-row">
        <div class="col-md-12 h-100">
            <div class="card room-card p-2 h-100">
                <div class="messages-container" id="messages">
                    <c:forEach var="message" items="${requestScope.messages}">
                        <c:if test="${sessionScope.currentUser.nickname == message.chatUserName}">
                            <div class="outgoing-bubble">
                                <p>${message.message}</p>
                            </div>
                        </c:if>
                        <c:if test="${sessionScope.currentUser.nickname != message.chatUserName}">
                            <div class="incoming-bubble">
                                <p><span class="name-in-bubble">${message.chatUserName}:</span> ${message.message}</p>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
                <form action="${pageContext.request.contextPath}/RoomController" method="post" class="flex-row mb-0">
                    <input type="text" class="form-control" name="messageInput" placeholder="Írj valamit..." autocomplete="off">
                    <input type="hidden" name="roomIdInput" value="${requestScope.chatRoom.id}">
                    <button type="submit" class="btn btn-primary col-md-1"><i class="far fa-paper-plane"></i></button>
                </form>
            </div>
        </div>

        <div class="col-md-7 h-100" style="min-width: 300px">
            <div class="card room-details-card p-2 h-100">
                <h3>Részletek</h3>
                <div>
                    <ul>
                        <li class="flex-row"><span>Szoba neve: </span> <p>${requestScope.chatRoom.roomName}</p></li>
                        <li class="flex-row"><span>Kategória: </span> <p>${requestScope.chatRoom.category}</p></li>
                    </ul>
                </div>

                <hr>

                <h3>Szabályzat</h3>
                <ul>
                    <c:forEach var="rule" items="${requestScope.chatRoom.rules}">
                        <li class="flex-row"><span>#${sorszam}: </span> <p>${rule.rule}</p></li>
                        <c:set var="sorszam" scope="session" value="${sorszam+1}"/>
                    </c:forEach>
                </ul>

                <hr>

                <form action="${pageContext.request.contextPath}/ListingController" method="get">
                    <button type="submit" class="btn btn-danger m-0">Szoba elhagyása</button>
                </form>
            </div>
        </div>
    </div>
</t:default-layout>
