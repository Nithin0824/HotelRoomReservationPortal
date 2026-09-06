package com.hotel;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/ViewReservationServlet")
public class ViewReservationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        String reservationId =
                request.getParameter("reservationId");


        String sql =
            "SELECT res.RESERVATION_ID, " +
            "       res.GUEST_NAME, " +
            "       r.ROOM_NUMBER, " +
            "       rt.TYPE_NAME, " +
            "       rt.BASE_PRICE, " +
            "       res.CHECK_IN, " +
            "       res.CHECK_OUT, " +
            "       res.GUESTS, " +
            "       res.STATUS " +
            "FROM RESERVATIONS res " +
            "JOIN HOTEL_ROOMS r " +
            "  ON res.ROOM_ID = r.ROOM_ID " +
            "JOIN ROOM_TYPES rt " +
            "  ON r.TYPE_ID = rt.TYPE_ID " +
            "WHERE res.RESERVATION_ID = ?";


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


            try (
                ResultSet resultSet =
                        statement.executeQuery()
            ) {


                if (resultSet.next()) {


                    int reservationIdValue =
                            resultSet.getInt(
                                    "RESERVATION_ID");


                    String guestName =
                            resultSet.getString(
                                    "GUEST_NAME");


                    int roomNumber =
                            resultSet.getInt(
                                    "ROOM_NUMBER");


                    String typeName =
                            resultSet.getString(
                                    "TYPE_NAME");


                    double price =
                            resultSet.getDouble(
                                    "BASE_PRICE");


                    java.sql.Date checkInDate =
                            resultSet.getDate(
                                    "CHECK_IN");


                    java.sql.Date checkOutDate =
                            resultSet.getDate(
                                    "CHECK_OUT");


                    int guests =
                            resultSet.getInt(
                                    "GUESTS");


                    String status =
                            resultSet.getString(
                                    "STATUS");


                    /*
                     * Calculate number of nights.
                     */
                    LocalDate checkIn =
                            checkInDate.toLocalDate();


                    LocalDate checkOut =
                            checkOutDate.toLocalDate();


                    long numberOfNights =
                            ChronoUnit.DAYS.between(
                                    checkIn,
                                    checkOut);


                    /*
                     * Calculate total amount.
                     */
                    double totalAmount =
                            numberOfNights * price;


                    /*
                     * Send reservation information
                     * to JSP.
                     */
                    request.setAttribute(
                            "reservationId",
                            reservationIdValue);


                    request.setAttribute(
                            "guestName",
                            guestName);


                    request.setAttribute(
                            "roomNumber",
                            roomNumber);


                    request.setAttribute(
                            "typeName",
                            typeName);


                    request.setAttribute(
                            "price",
                            price);


                    request.setAttribute(
                            "checkIn",
                            checkInDate);


                    request.setAttribute(
                            "checkOut",
                            checkOutDate);


                    request.setAttribute(
                            "guests",
                            guests);


                    request.setAttribute(
                            "status",
                            status);


                    request.setAttribute(
                            "numberOfNights",
                            numberOfNights);


                    request.setAttribute(
                            "amount",
                            totalAmount);


                    RequestDispatcher dispatcher =
                            request.getRequestDispatcher(
                                    "viewReservation.jsp");


                    dispatcher.forward(
                            request,
                            response);


                } else {


                    response.setContentType(
                            "text/html;charset=UTF-8");


                    response.getWriter().println(
                            "<h1>Reservation Not Found</h1>");


                    response.getWriter().println(
                            "<p>No reservation was found with ID: "
                            + reservationId
                            + "</p>");


                    response.getWriter().println(
                            "<p>Please check your Reservation ID "
                            + "and try again.</p>");
                }
            }


        } catch (
                SQLException |
                NumberFormatException e) {


            throw new ServletException(
                    "Error retrieving reservation.",
                    e);
        }
    }


    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        doGet(
                request,
                response);
    }
}