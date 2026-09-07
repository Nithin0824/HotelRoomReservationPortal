package com.hotel;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String username =
                request.getParameter("username");

        String password =
                request.getParameter("password");

        // Temporary admin credentials for the case study
        if ("admin".equals(username)
                && "admin123".equals(password)) {

            response.sendRedirect(
                    "adminDashboard.jsp");

        } else {

            response.setContentType(
                    "text/html;charset=UTF-8");

            response.getWriter().println(
                    "<h1>Admin Login Failed</h1>");

            response.getWriter().println(
                    "<p>Invalid username or password.</p>");

            response.getWriter().println(
                    "<p><a href=\"adminLogin.jsp\">" +
                    "Try Again</a></p>");
        }
    }

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect("adminLogin.jsp");
    }
}
