package com.hotel;

import java.io.IOException;
import java.time.temporal.ChronoUnit;

import com.hotel.model.Reservation;
import com.hotel.model.Room;
import com.hotel.service.PaymentService;
import com.hotel.service.ReservationService;
import com.hotel.service.RoomService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UpgradePaymentServlet")
public class UpgradePaymentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ReservationService reservationService;
    private RoomService roomService;
    private PaymentService paymentService;


    @Override
    public void init() throws ServletException {

        reservationService =
                new ReservationService();

        roomService =
                new RoomService();

        paymentService =
                new PaymentService();
    }


    /*
     * GET:
     *
     * Display upgrade payment confirmation.
     */
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int reservationId =
                    Integer.parseInt(
                            request.getParameter(
                                    "reservationId"));

            int newRoomId =
                    Integer.parseInt(
                            request.getParameter(
                                    "newRoomId"));


            /*
             * Find reservation.
             */
            Reservation reservation =
                    reservationService.findById(
                            reservationId);

            if (reservation == null) {

                throw new ServletException(
                        "Reservation could not be found.");
            }


            /*
             * Only BOOKED reservations can be upgraded.
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
             * Find current room.
             */
            Room currentRoom =
                    roomService.findRoomById(
                            reservation.getRoomId());

            if (currentRoom == null) {

                throw new ServletException(
                        "Current room could not be found.");
            }


            /*
             * Find new room.
             */
            Room newRoom =
                    roomService.findRoomById(
                            newRoomId);

            if (newRoom == null) {

                throw new ServletException(
                        "Selected room could not be found.");
            }


            /*
             * New room must be more expensive.
             */
            if (newRoom.getBasePrice()
                    <= currentRoom.getBasePrice()) {

                throw new ServletException(
                        "Selected room is not an upgrade.");
            }


            /*
             * Calculate nights.
             */
            long numberOfNights =
                    ChronoUnit.DAYS.between(
                            reservation.getCheckIn(),
                            reservation.getCheckOut());


            if (numberOfNights <= 0) {

                throw new ServletException(
                        "Invalid reservation dates.");
            }


            /*
             * Calculate additional payment.
             */
            double priceDifference =
                    newRoom.getBasePrice()
                    - currentRoom.getBasePrice();

            double additionalAmount =
                    priceDifference
                    * numberOfNights;


            /*
             * Send information to payment page.
             */
            request.setAttribute(
                    "reservation",
                    reservation);

            request.setAttribute(
                    "currentRoom",
                    currentRoom);

            request.setAttribute(
                    "newRoom",
                    newRoom);

            request.setAttribute(
                    "numberOfNights",
                    numberOfNights);

            request.setAttribute(
                    "priceDifference",
                    priceDifference);

            request.setAttribute(
                    "additionalAmount",
                    additionalAmount);


            RequestDispatcher dispatcher =
                    request.getRequestDispatcher(
                            "upgradePayment.jsp");

            dispatcher.forward(
                    request,
                    response);


        } catch (NumberFormatException e) {

            throw new ServletException(
                    "Invalid reservation ID or room ID.",
                    e);
        }
    }


    /*
     * POST:
     *
     * Actually complete the room upgrade.
     */
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int reservationId =
                    Integer.parseInt(
                            request.getParameter(
                                    "reservationId"));

            int newRoomId =
                    Integer.parseInt(
                            request.getParameter(
                                    "newRoomId"));

            String paymentMethod =
                    request.getParameter(
                            "paymentMethod");


            /*
             * Validate payment method.
             */
            if (paymentMethod == null ||
                paymentMethod.trim().isEmpty()) {

                request.setAttribute(
                        "errorMessage",
                        "Please select a payment method.");

                doGet(request, response);

                return;
            }


            /*
             * Complete the upgrade.
             *
             * This performs:
             *
             * INSERT upgrade payment
             * +
             * UPDATE reservation ROOM_ID
             *
             * in ONE transaction.
             */
            int paymentId =
                    paymentService.completeRoomUpgrade(
                            reservationId,
                            newRoomId,
                            paymentMethod);


            /*
             * Redirect to reservation page.
             */
            response.sendRedirect(
                    "ViewReservationServlet?reservationId="
                    + reservationId
                    + "&upgradeSuccess=true"
                    + "&paymentId="
                    + paymentId);


        } catch (NumberFormatException e) {

            throw new ServletException(
                    "Invalid reservation ID or room ID.",
                    e);

        } catch (RuntimeException e) {

            request.setAttribute(
                    "errorMessage",
                    e.getMessage());

            try {

                doGet(request, response);

            } catch (Exception ex) {

                throw new ServletException(
                        "Unable to display upgrade page.",
                        ex);
            }
        }
    }
}
