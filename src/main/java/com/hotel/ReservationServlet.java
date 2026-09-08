package com.hotel;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.hotel.dao.ReservationDAO.RoomDetails;
import com.hotel.model.Reservation;
import com.hotel.service.ReservationService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ReservationServlet")
public class ReservationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        reservationService = new ReservationService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String roomId = request.getParameter("roomId");
        String checkIn = request.getParameter("checkIn");
        String checkOut = request.getParameter("checkOut");
        String guests = request.getParameter("guests");

        try {

            int roomIdValue = Integer.parseInt(roomId);

            // Get room details through Service → DAO
            RoomDetails roomDetails =
                    reservationService.findRoomDetails(roomIdValue);

            if (roomDetails == null) {
                throw new ServletException(
                        "Room details could not be found.");
            }

            // Calculate number of nights
            LocalDate checkInDate =
                    LocalDate.parse(checkIn);

            LocalDate checkOutDate =
                    LocalDate.parse(checkOut);

            long numberOfNights =
                    ChronoUnit.DAYS.between(
                            checkInDate,
                            checkOutDate);

            // Calculate total amount
            double totalAmount =
                    numberOfNights *
                    roomDetails.getPrice();

            // Send data to reservation.jsp
            request.setAttribute(
                    "roomId",
                    roomId);

            request.setAttribute(
                    "roomDetails",
                    roomDetails);

            request.setAttribute(
                    "checkIn",
                    checkIn);

            request.setAttribute(
                    "checkOut",
                    checkOut);

            request.setAttribute(
                    "guests",
                    guests);

            request.setAttribute(
                    "numberOfNights",
                    numberOfNights);

            request.setAttribute(
                    "totalAmount",
                    totalAmount);

            RequestDispatcher dispatcher =
                    request.getRequestDispatcher(
                            "reservation.jsp");

            dispatcher.forward(
                    request,
                    response);

        } catch (NumberFormatException e) {

            throw new ServletException(
                    "Invalid room ID.",
                    e);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String roomId =
                request.getParameter("roomId");

        String guestName =
                request.getParameter("guestName");

        String checkIn =
                request.getParameter("checkIn");

        String checkOut =
                request.getParameter("checkOut");

        String guests =
                request.getParameter("guests");

        /*
         * Store reservation wizard details
         * temporarily in the user's HTTP session.
         */
        HttpSession session =
                request.getSession();

        session.setAttribute(
                "guestName",
                guestName);

        session.setAttribute(
                "checkIn",
                checkIn);

        session.setAttribute(
                "checkOut",
                checkOut);

        session.setAttribute(
                "guests",
                guests);

        session.setAttribute(
                "roomId",
                roomId);

        try {

            int roomIdValue =
                    Integer.parseInt(roomId);

            int guestCount =
                    Integer.parseInt(guests);

            // Check room availability
            boolean alreadyBooked =
                    reservationService.isRoomBooked(
                            roomIdValue,
                            checkIn,
                            checkOut);

            if (alreadyBooked) {

                response.setContentType(
                        "text/html;charset=UTF-8");

                response.getWriter().println(
                        "<h1>Room Not Available</h1>");

                response.getWriter().println(
                        "<p>Sorry, this room is already booked " +
                        "for the selected dates.</p>");

                response.getWriter().println(
                        "<p>Please go back and choose another " +
                        "room or different dates.</p>");

                response.getWriter().println(
                        "<br><a href=\"welcome.jsp\">" +
                        "Back to Hotel Reservation Portal" +
                        "</a>");

                return;
            }

            // Create Reservation object
            Reservation reservation =
                    new Reservation();

            reservation.setRoomId(
                    roomIdValue);

            reservation.setGuestName(
                    guestName);

            reservation.setCheckIn(
                    LocalDate.parse(checkIn));

            reservation.setCheckOut(
                    LocalDate.parse(checkOut));

            reservation.setGuests(
                    guestCount);

            /*
             * New reservations are PENDING.
             * They become BOOKED after successful payment.
             */
            reservation.setStatus(
                    "PENDING");

            // Save reservation
            int reservationId =
                    reservationService.createReservation(
                            reservation);

            // Get room details
            RoomDetails roomDetails =
                    reservationService.findRoomDetails(
                            roomIdValue);

            if (roomDetails == null) {

                throw new ServletException(
                        "Room details could not be found.");
            }

            int roomNumber =
                    roomDetails.getRoomNumber();

            String typeName =
                    roomDetails.getTypeName();

            double price =
                    roomDetails.getPrice();

            // Calculate number of nights
            LocalDate checkInDate =
                    LocalDate.parse(checkIn);

            LocalDate checkOutDate =
                    LocalDate.parse(checkOut);

            long numberOfNights =
                    ChronoUnit.DAYS.between(
                            checkInDate,
                            checkOutDate);

            double totalAmount =
                    numberOfNights * price;

            // Send reservation details to confirmation.jsp
            request.setAttribute(
                    "reservationId",
                    reservationId);

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
                    "checkIn",
                    checkIn);

            request.setAttribute(
                    "checkOut",
                    checkOut);

            request.setAttribute(
                    "guests",
                    guestCount);

            request.setAttribute(
                    "numberOfNights",
                    numberOfNights);

            request.setAttribute(
                    "price",
                    price);

            request.setAttribute(
                    "totalAmount",
                    totalAmount);

            RequestDispatcher dispatcher =
                    request.getRequestDispatcher(
                            "confirmation.jsp");

            dispatcher.forward(
                    request,
                    response);

        } catch (NumberFormatException e) {

            throw new ServletException(
                    "Invalid reservation details.",
                    e);
        }
    }
}
