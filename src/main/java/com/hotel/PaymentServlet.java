package com.hotel;

import java.io.IOException;

import com.hotel.model.Payment;
import com.hotel.service.PaymentService;

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


    @Override
    public void init() throws ServletException {

        paymentService =
                new PaymentService();
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
             * Complete payment and reservation booking
             * in ONE database transaction.
             *
             * Payment INSERT and reservation UPDATE
             * will either both succeed or both rollback.
             */
            int paymentId =
                    paymentService.completePayment(
                            payment);


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

        } catch (RuntimeException e) {

            throw new ServletException(
                    "Payment could not be completed. " +
                    "The reservation was not changed.",
                    e);
        }
    }
}