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

@WebServlet("/AdminReservationsServlet")
public class AdminReservationsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String sql =
            "SELECT res.RESERVATION_ID, " +
            "       res.GUEST_NAME, " +
            "       r.ROOM_NUMBER, " +
            "       rt.TYPE_NAME, " +
            "       res.CHECK_IN, " +
            "       res.CHECK_OUT, " +
            "       res.GUESTS, " +
            "       res.STATUS " +
            "FROM RESERVATIONS res " +
            "JOIN HOTEL_ROOMS r " +
            "  ON res.ROOM_ID = r.ROOM_ID " +
            "JOIN ROOM_TYPES rt " +
            "  ON r.TYPE_ID = rt.TYPE_ID " +
            "ORDER BY res.RESERVATION_ID DESC";

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
                            "adminReservations.jsp");

            dispatcher.forward(
                    request,
                    response);

        } catch (SQLException e) {

            throw new ServletException(
                    "Error retrieving reservations.",
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