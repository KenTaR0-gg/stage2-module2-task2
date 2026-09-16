package com.example.servlet;

import com.example.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        boolean hasUserAttribute = (session != null && session.getAttribute("user") != null);

        if (hasUserAttribute) {
            resp.sendRedirect("/user/hello.jsp");
        } else {
            resp.sendRedirect("/login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        if (login == null) {
            login = (String) req.getAttribute("login");
        }

        String password = req.getParameter("password");
        if (password == null) {
            password = (String) req.getAttribute("password");
        }

        boolean isLoginValid = login != null && Users.getInstance().getUsers().contains(login);
        boolean isPasswordNotBlank = password != null && !password.trim().isEmpty();

        if (isLoginValid && isPasswordNotBlank) {
            req.getSession().setAttribute("user", login);
            resp.sendRedirect("/user/hello.jsp");
        } else {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}