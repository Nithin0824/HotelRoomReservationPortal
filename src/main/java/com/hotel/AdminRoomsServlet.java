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

@WebServlet("/AdminRoomsServlet")
public class AdminRoomsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String sql =
            "SELECT r.ROOM_ID, " +
            "       r.ROOM_NUMBER, " +
            "       rt.TYPE_NAME, " +
            "       rt.BASE_PRICE, " +
            "       r.STATUS " +
            "FROM HOTEL_ROOMS r " +
            "JOIN ROOM_TYPES rt " +
            "  ON r.TYPE_ID = rt.TYPE_ID " +
            "ORDER BY r.ROOM_NUMBER";

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
                            "adminRooms.jsp");

            dispatcher.forward(
                    request,
                    response);

        } catch (SQLException e) {

            throw new ServletException(
                    "Error retrieving room information.",
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