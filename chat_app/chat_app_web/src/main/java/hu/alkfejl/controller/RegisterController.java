package hu.alkfejl.controller;

import hu.alkfejl.dao.ChatUserDAO;
import hu.alkfejl.dao.ChatUserDAOImpl;
import hu.alkfejl.model.ChatUser;
import hu.alkfejl.model.Gender;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {

    ChatUserDAO chatUserDAO = ChatUserDAOImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        String registerNickname = req.getParameter("nickname");
        String registerPassword = req.getParameter("password");
        String registerPasswordAgain = req.getParameter("passwordAgain");
        String error;

        if (chatUserDAO.findUserByNickname(registerNickname) != null) {
            error = "Ez a felhasználónév már foglalt!";
            req.setAttribute("error", error);
            System.out.println(error);
            req.getRequestDispatcher("pages/register.jsp").forward(req, resp);
            return;
        } else if (!registerPassword.equals(registerPasswordAgain)) {
            error = "A megadott jelszavak nem egyeznek!";
            req.setAttribute("error", error);
            System.out.println(error);
            req.getRequestDispatcher("pages/register.jsp").forward(req, resp);
            return;
        } else {
            ChatUser chatUser = new ChatUser();
            chatUser.setNickname(req.getParameter("nickname"));
            chatUser.setPassword(req.getParameter("password"));
            chatUser.setAge(Integer.parseInt(req.getParameter("age")));
            chatUser.setInterest(req.getParameter("interest"));
            chatUser.setGender(Gender.valueOf(req.getParameter("gender")));

            chatUserDAO.save(chatUser);
        }

        req.setAttribute("error", "Sikeres regisztráció!");
        req.getRequestDispatcher("pages/login.jsp").forward(req, resp);
    }
}
