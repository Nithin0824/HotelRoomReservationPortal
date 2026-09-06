package com.hotel;

import java.io.IOException;

import com.hotel.model.Payment;
import com.hotel.service.PaymentService;
import com.hotel.service.ReservationService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private PaymentService paymentService;
    private ReservationService reservationService;


    @Override
    public void init() throws ServletException {

        paymentService =
                new PaymentService();

        reservationService =
                new ReservationService();
    }


    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String reservationId =
                request.getParameter("reservationId");

        String amount =
                request.getParameter("amount");


        request.setAttribute(
                "reservationId",
                reservationId);


        request.setAttribute(
                "amount",
                amount);


        RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                        "payment.jsp");


        dispatcher.forward(
                request,
                response);
    }


    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String reservationId =
                request.getParameter("reservationId");

        String amount =
                request.getParameter("amount");

        String paymentMethod =
                request.getParameter("paymentMethod");


        try {

            int reservationIdValue =
                    Integer.parseInt(
                            reservationId);


            double amountValue =
                    Double.parseDouble(
                            amount);


            /*
             * Create Payment object.
             */
            Payment payment =
                    new Payment();


            payment.setReservationId(
                    reservationIdValue);


            payment.setPaymentMethod(
                    paymentMethod);


            payment.setAmount(
                    amountValue);


            payment.setPaymentStatus(
                    "SUCCESS");


            /*
             * Save payment.
             *
             * This payment belongs to the
             * EXISTING reservation.
             */
            int paymentId =
                    paymentService.createPayment(
                            payment);


            /*
             * IMPORTANT:
             *
             * Update the SAME reservation
             * from PENDING to BOOKED.
             *
             * No new reservation is created.
             */
            boolean updated =
                    reservationService.updateStatus(
                            reservationIdValue,
                            "BOOKED");


            if (!updated) {

                throw new ServletException(
                        "Payment was saved, but the reservation " +
                        "could not be changed to BOOKED.");
            }


            /*
             * Send payment details to
             * paymentSuccess.jsp.
             */
            request.setAttribute(
                    "paymentId",
                    paymentId);


            request.setAttribute(
                    "reservationId",
                    reservationIdValue);


            request.setAttribute(
                    "paymentMethod",
                    paymentMethod);


            request.setAttribute(
                    "amount",
                    amountValue);


            request.setAttribute(
                    "paymentStatus",
                    "SUCCESS");


            RequestDispatcher dispatcher =
                    request.getRequestDispatcher(
                            "paymentSuccess.jsp");


            dispatcher.forward(
                    request,
                    response);


        } catch (NumberFormatException e) {

            throw new ServletException(
                    "Invalid payment details.",
                    e);
        }
    }
}