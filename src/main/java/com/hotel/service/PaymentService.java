package com.hotel.service;

import com.hotel.dao.PaymentDAO;
import com.hotel.model.Payment;

public class PaymentService {

    private PaymentDAO paymentDAO;

    public PaymentService() {
        paymentDAO = new PaymentDAO();
    }

    public int createPayment(Payment payment) {
        return paymentDAO.createPayment(payment);
    }

    public Payment findByReservationId(int reservationId) {
        return paymentDAO.findByReservationId(reservationId);
    }
}
