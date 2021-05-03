package hu.alkfejl.controller;

import hu.alkfejl.dao.*;
import hu.alkfejl.model.ChatRoom;
import hu.alkfejl.model.ChatUser;
import hu.alkfejl.model.Message;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/RoomController")
public class RoomController extends HttpServlet {
    private ChatRoomDAO chatRoomDAO = ChatRoomDAOImpl.getInstance();
    private MessageDAO messageDAO = MessageDAOImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        ChatUser chatUser = (ChatUser) req.getSession().getAttribute("currentUser");

        if (req.getParameter("messageInput") != null && !req.getParameter("messageInput").isEmpty()) {
            Message message = new Message();
            message.setMessage(req.getParameter("messageInput"));
            message.setChatUserId(chatUser.getId());
            message.setChatRoomId(Integer.parseInt(req.getParameter("roomIdInput")));
            messageDAO.save(message);
        }

        resp.sendRedirect("RoomController?roomId=" + req.getParameter("roomIdInput"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roomIdStr = req.getParameter("roomId");

        if(roomIdStr != null && !roomIdStr.isEmpty()){
            int roomId = Integer.parseInt(roomIdStr);
            ChatRoom chatRoom = chatRoomDAO.findRoomById(roomId);
            List<Message> messages = messageDAO.findAllMessagesByRoom(roomId);

            req.setAttribute("messages", messages);
            req.setAttribute("chatRoom", chatRoom);
        }

        req.getRequestDispatcher("pages/room.jsp").forward(req, resp);
    }
}
