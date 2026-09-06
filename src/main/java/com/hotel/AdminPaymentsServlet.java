package com.hotel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AdminPaymentsServlet")
 public class AdminPaymentsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String sql =
            "SELECT PAYMENT_ID, " +
            "       RESERVATION_ID, " +
            "       PAYMENT_METHOD, " +
            "       AMOUNT, " +
            "       PAYMENT_STATUS, " +
            "       PAYMENT_DATE " +
            "FROM PAYMENTS " +
            "ORDER BY PAYMENT_ID DESC";

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet resultSet =
                    statement.executeQuery()
        ) {

            request.setAttribute(
                    "resultSet",
                    resultSet);

            RequestDispatcher dispatcher =
                    request.getRequestDispatcher(
                            "adminPayments.jsp");

            dispatcher.forward(
                    request,
                    response);

        } catch (SQLException e) {

            throw new ServletException(
                    "Error retrieving payment information.",
                    e);
        }
    }

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}