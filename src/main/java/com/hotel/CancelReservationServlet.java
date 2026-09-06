package com.hotel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CancelReservationServlet")
public class CancelReservationServlet extends HttpServlet {

private static final long serialVersionUID = 1L;

protected void doGet(HttpServletRequest request,
                     HttpServletResponse response)
        throws ServletException, IOException {

    String reservationId =
            request.getParameter("reservationId");

    String sql =
        "UPDATE RESERVATIONS " +
        "SET STATUS = 'CANCELLED' " +
        "WHERE RESERVATION_ID = ? " +
        "AND STATUS = 'BOOKED'";

    try (
        Connection connection =
                DBConnection.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(sql)
    ) {

        statement.setInt(
                1,
                Integer.parseInt(reservationId)
        );

        int rowsUpdated =
                statement.executeUpdate();
      


        request.setAttribute(
                "reservationId",
                reservationId
        );

        request.setAttribute(
                "cancelled",
                rowsUpdated > 0
        );

        RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                        "cancellation.jsp"
                );

        dispatcher.forward(request, response);

    } catch (SQLException | NumberFormatException e) {

        throw new ServletException(
                "Error cancelling reservation.",
                e
        );
    }
}

protected void doPost(HttpServletRequest request,
                      HttpServletResponse response)
        throws ServletException, IOException {

    doGet(request, response);
}


}