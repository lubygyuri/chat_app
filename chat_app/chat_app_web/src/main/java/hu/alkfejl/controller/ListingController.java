package hu.alkfejl.controller;

import hu.alkfejl.dao.ChatRoomDAO;
import hu.alkfejl.dao.ChatRoomDAOImpl;
import hu.alkfejl.dao.ChatUserDAO;
import hu.alkfejl.dao.ChatUserDAOImpl;
import hu.alkfejl.model.ChatRoom;
import hu.alkfejl.model.ChatUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ListingController")
public class ListingController extends HttpServlet {
    private ChatUserDAO chatUserDAO = ChatUserDAOImpl.getInstance();
    private ChatRoomDAO chatRoomDAO = ChatRoomDAOImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getParameter("chatUserSearchList");
        req.getParameter("chatRoomSearchList");
        this.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ChatUser> chatUserList = chatUserDAO.findAll();
        List<ChatRoom> chatRoomList = chatRoomDAO.findAll();
        req.setAttribute("chatUserList", chatUserList);
        req.setAttribute("chatRoomList", chatRoomList);
        req.getRequestDispatcher("pages/main.jsp").forward(req, resp);
    }
}
