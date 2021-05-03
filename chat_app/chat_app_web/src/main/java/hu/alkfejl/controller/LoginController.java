package hu.alkfejl.controller;

import hu.alkfejl.dao.ChatUserDAO;
import hu.alkfejl.dao.ChatUserDAOImpl;
import hu.alkfejl.model.ChatUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {

    public LoginController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        ChatUserDAO userDAO = ChatUserDAOImpl.getInstance();

        String nickname = req.getParameter("nickname");
        String password = req.getParameter("password");

        ChatUser chatUser = userDAO.login(nickname, password);

        if( chatUser == null){
            req.setAttribute("error", "Nem megfelelő felhasználónév vagy jelszó!");
            req.getRequestDispatcher("pages/login.jsp").forward(req, resp);
            return;
        }

        req.getSession().setAttribute("currentUser", chatUser);
        resp.sendRedirect("ListingController");
    }
}
