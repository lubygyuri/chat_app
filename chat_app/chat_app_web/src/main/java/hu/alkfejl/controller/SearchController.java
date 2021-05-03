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

@WebServlet("/SearchController")
public class SearchController extends HttpServlet {
    private ChatUserDAO chatUserDAO = ChatUserDAOImpl.getInstance();
    private ChatRoomDAO chatRoomDAO = ChatRoomDAOImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        req.setAttribute("chatUserSearchList", null);
        req.setAttribute("chatRoomSearchList", null);

        String searchingFor = req.getParameter("search");
        String searchingBy = req.getParameter("searchCategory");

        if (searchingFor != null && !searchingFor.isEmpty()) {
            searchingFor = '%' +  searchingFor + '%';

            switch (searchingBy) {
                case "roomName": {
                    List<ChatRoom> chatRoomList = chatRoomDAO.searchByRoomName(searchingFor);
                    req.setAttribute("chatRoomSearchList", chatRoomList);
                    break;
                }
                case "category": {
                    List<ChatRoom> chatRoomList = chatRoomDAO.searchByCategory(searchingFor);
                    req.setAttribute("chatRoomSearchList", chatRoomList);
                    break;
                }
                case "nickname": {
                    List<ChatUser> chatUserList = chatUserDAO.searchByNickname(searchingFor);
                    req.setAttribute("chatUserSearchList", chatUserList);
                    break;
                }
                case "interest": {
                    List<ChatUser> chatUserList = chatUserDAO.searchByInterest(searchingFor);
                    req.setAttribute("chatUserSearchList", chatUserList);
                    break;
                }
            }

        }

        req.getRequestDispatcher("ListingController").forward(req, resp);
    }
}
