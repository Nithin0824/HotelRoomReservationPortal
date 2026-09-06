package com.hotel.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hotel.DBConnection;
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


    /*
     * Complete payment and booking in ONE database transaction.
     *
     * Payment is inserted and the reservation is changed
     * from PENDING to BOOKED using the same connection.
     *
     * If either operation fails, everything is rolled back.
     */
    public int completePayment(Payment payment) {

        String paymentSql =
            "INSERT INTO PAYMENTS " +
            "(RESERVATION_ID, PAYMENT_METHOD, AMOUNT, " +
            "PAYMENT_STATUS, PAYMENT_DATE) " +
            "VALUES (?, ?, ?, ?, SYSDATE)";


        String reservationSql =
            "UPDATE RESERVATIONS " +
            "SET STATUS = 'BOOKED' " +
            "WHERE RESERVATION_ID = ? " +
            "AND STATUS = 'PENDING'";


        try (
            Connection connection =
                    DBConnection.getConnection()
        ) {

            /*
             * Start transaction.
             */
            connection.setAutoCommit(false);


            try {

                int paymentId = 0;


                /*
                 * STEP 1:
                 * Insert payment.
                 */
                try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    paymentSql,
                                    new String[] {"PAYMENT_ID"})
                ) {

                    statement.setInt(
                            1,
                            payment.getReservationId());

                    statement.setString(
                            2,
                            payment.getPaymentMethod());

                    statement.setDouble(
                            3,
                            payment.getAmount());

                    statement.setString(
                            4,
                            payment.getPaymentStatus());


                    int rowsInserted =
                            statement.executeUpdate();


                    if (rowsInserted == 0) {

                        throw new SQLException(
                                "Payment could not be created.");
                    }


                    try (
                        ResultSet generatedKeys =
                                statement.getGeneratedKeys()
                    ) {

                        if (generatedKeys.next()) {

                            paymentId =
                                    generatedKeys.getInt(1);

                        } else {

                            throw new SQLException(
                                    "Payment ID could not be generated.");
                        }
                    }
                }


                /*
                 * STEP 2:
                 * Change reservation from PENDING to BOOKED.
                 */
                try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    reservationSql)
                ) {

                    statement.setInt(
                            1,
                            payment.getReservationId());


                    int rowsUpdated =
                            statement.executeUpdate();


                    if (rowsUpdated == 0) {

                        throw new SQLException(
                                "Reservation could not be changed " +
                                "to BOOKED.");
                    }
                }


                /*
                 * STEP 3:
                 * Both operations succeeded.
                 * Commit the transaction.
                 */
                connection.commit();


                return paymentId;


            } catch (SQLException e) {

                /*
                 * Something failed.
                 * Undo both database operations.
                 */
                connection.rollback();

                throw new RuntimeException(
                        "Payment transaction failed. " +
                        "All changes were rolled back.",
                        e);
            }


        } catch (SQLException e) {

            throw new RuntimeException(
                    "Database transaction error.",
                    e);
        }
    }
}