package com.hotel;

import java.io.IOException;
import java.util.List;

import com.hotel.model.Reservation;
import com.hotel.model.Room;
import com.hotel.service.ReservationService;
import com.hotel.service.RoomService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UpgradeRoomServlet")
public class UpgradeRoomServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ReservationService reservationService;
    private RoomService roomService;

    @Override
    public void init() throws ServletException {

        reservationService =
                new ReservationService();

        roomService =
                new RoomService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String reservationId =
                request.getParameter("reservationId");

        try {

            int reservationIdValue =
                    Integer.parseInt(reservationId);

            /*
             * Find the reservation.
             */
            Reservation reservation =
                    reservationService.findById(
                            reservationIdValue);

            if (reservation == null) {

                throw new ServletException(
                        "Reservation could not be found.");
            }

            /*
             * Upgrade is allowed only for
             * BOOKED reservations.
             */
            if (!"BOOKED".equals(
                    reservation.getStatus())) {

                request.setAttribute(
                        "errorMessage",
                        "Only BOOKED reservations " +
                        "can be upgraded.");

                RequestDispatcher dispatcher =
                        request.getRequestDispatcher(
                                "viewReservation.jsp");

                dispatcher.forward(
                        request,
                        response);

                return;
            }

            /*
             * Find the current room.
             */
            Room currentRoom =
                    roomService.findRoomById(
                            reservation.getRoomId());

            if (currentRoom == null) {

                throw new ServletException(
                        "Current room could not be found.");
            }

            /*
             * Find available rooms that are
             * more expensive than the current room.
             */
            List<Room> upgradeRooms =
                    roomService.findUpgradeRooms(
                            reservation.getRoomId(),
                            reservation.getCheckIn().toString(),
                            reservation.getCheckOut().toString());

            /*
             * Send data to upgradeRoom.jsp.
             */
            request.setAttribute(
                    "reservation",
                    reservation);

            request.setAttribute(
                    "currentRoom",
                    currentRoom);

            request.setAttribute(
                    "upgradeRooms",
                    upgradeRooms);

            RequestDispatcher dispatcher =
                    request.getRequestDispatcher(
                            "upgradeRoom.jsp");

            dispatcher.forward(
                    request,
                    response);

        } catch (NumberFormatException e) {

            throw new ServletException(
                    "Invalid reservation ID.",
                    e);
        }
    }
}

