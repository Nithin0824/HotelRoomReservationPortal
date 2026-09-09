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
     * Complete normal payment and booking in ONE database transaction.
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
                 * Commit.
                 */
                connection.commit();

                return paymentId;


            } catch (SQLException e) {

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


    /*
     * Complete a ROOM UPGRADE in ONE database transaction.
     *
     * The original reservation is kept.
     * Only ROOM_ID is changed.
     *
     * A NEW payment is inserted for the additional amount.
     *
     * Example:
     *
     * Old room       = Rs. 4000/night
     * New room       = Rs. 7000/night
     * Nights         = 2
     *
     * Additional payment = (7000 - 4000) * 2
     *                    = Rs. 6000
     *
     * Transaction:
     *
     * 1. Lock reservation
     * 2. Validate new room
     * 3. Check room availability
     * 4. Insert upgrade payment
     * 5. Change reservation ROOM_ID
     * 6. Commit
     *
     * If anything fails:
     * ROLLBACK
     */
    public int completeRoomUpgrade(
            int reservationId,
            int newRoomId,
            String paymentMethod) {

        try (
            Connection connection =
                    DBConnection.getConnection()
        ) {

            connection.setAutoCommit(false);

            try {

                /*
                 * =================================================
                 * STEP 1
                 * Lock and read the existing reservation.
                 * =================================================
                 */
                String reservationSql =
                    "SELECT ROOM_ID, CHECK_IN, CHECK_OUT, STATUS " +
                    "FROM RESERVATIONS " +
                    "WHERE RESERVATION_ID = ? " +
                    "FOR UPDATE";

                int currentRoomId;
                java.sql.Date checkIn;
                java.sql.Date checkOut;
                String reservationStatus;

                try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    reservationSql)
                ) {

                    statement.setInt(
                            1,
                            reservationId);

                    try (
                        ResultSet resultSet =
                                statement.executeQuery()
                    ) {

                        if (!resultSet.next()) {

                            throw new SQLException(
                                    "Reservation not found.");
                        }

                        currentRoomId =
                                resultSet.getInt("ROOM_ID");

                        checkIn =
                                resultSet.getDate("CHECK_IN");

                        checkOut =
                                resultSet.getDate("CHECK_OUT");

                        reservationStatus =
                                resultSet.getString("STATUS");
                    }
                }


                /*
                 * Upgrade is allowed only for BOOKED reservations.
                 */
                if (!"BOOKED".equals(reservationStatus)) {

                    throw new SQLException(
                            "Only BOOKED reservations " +
                            "can be upgraded.");
                }


                /*
                 * Cannot upgrade to the same room.
                 */
                if (currentRoomId == newRoomId) {

                    throw new SQLException(
                            "The new room must be different " +
                            "from the current room.");
                }


                /*
                 * =================================================
                 * STEP 2
                 * Get current room price.
                 * =================================================
                 */
                String currentRoomSql =
                    "SELECT rt.BASE_PRICE " +
                    "FROM HOTEL_ROOMS r " +
                    "JOIN ROOM_TYPES rt " +
                    "ON r.TYPE_ID = rt.TYPE_ID " +
                    "WHERE r.ROOM_ID = ?";

                double currentPrice;

                try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    currentRoomSql)
                ) {

                    statement.setInt(
                            1,
                            currentRoomId);

                    try (
                        ResultSet resultSet =
                                statement.executeQuery()
                    ) {

                        if (!resultSet.next()) {

                            throw new SQLException(
                                    "Current room could not be found.");
                        }

                        currentPrice =
                                resultSet.getDouble("BASE_PRICE");
                    }
                }


                /*
                 * =================================================
                 * STEP 3
                 * Lock and read the new room.
                 * =================================================
                 */
                String newRoomSql =
                    "SELECT r.STATUS, rt.BASE_PRICE " +
                    "FROM HOTEL_ROOMS r " +
                    "JOIN ROOM_TYPES rt " +
                    "ON r.TYPE_ID = rt.TYPE_ID " +
                    "WHERE r.ROOM_ID = ? " +
                    "FOR UPDATE";

                String newRoomStatus;
                double newRoomPrice;

                try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    newRoomSql)
                ) {

                    statement.setInt(
                            1,
                            newRoomId);

                    try (
                        ResultSet resultSet =
                                statement.executeQuery()
                    ) {

                        if (!resultSet.next()) {

                            throw new SQLException(
                                    "New room could not be found.");
                        }

                        newRoomStatus =
                                resultSet.getString("STATUS");

                        newRoomPrice =
                                resultSet.getDouble("BASE_PRICE");
                    }
                }


                /*
                 * New room must be AVAILABLE.
                 */
                if (!"AVAILABLE".equals(newRoomStatus)) {

                    throw new SQLException(
                            "The selected room is not available.");
                }


                /*
                 * New room must be more expensive.
                 */
                if (newRoomPrice <= currentPrice) {

                    throw new SQLException(
                            "The selected room is not an upgrade.");
                }


                /*
                 * =================================================
                 * STEP 4
                 * Make sure the new room is not booked for the
                 * reservation dates.
                 *
                 * We exclude the current reservation just in case.
                 * =================================================
                 */
                String availabilitySql =
                    "SELECT COUNT(*) " +
                    "FROM RESERVATIONS " +
                    "WHERE ROOM_ID = ? " +
                    "AND RESERVATION_ID <> ? " +
                    "AND STATUS = 'BOOKED' " +
                    "AND CHECK_IN < ? " +
                    "AND CHECK_OUT > ?";

                try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    availabilitySql)
                ) {

                    statement.setInt(
                            1,
                            newRoomId);

                    statement.setInt(
                            2,
                            reservationId);

                    statement.setDate(
                            3,
                            checkOut);

                    statement.setDate(
                            4,
                            checkIn);

                    try (
                        ResultSet resultSet =
                                statement.executeQuery()
                    ) {

                        if (resultSet.next()) {

                            int bookedCount =
                                    resultSet.getInt(1);

                            if (bookedCount > 0) {

                                throw new SQLException(
                                        "The selected room is already " +
                                        "booked for these dates.");
                            }
                        }
                    }
                }


                /*
                 * =================================================
                 * STEP 5
                 * Calculate number of nights.
                 * =================================================
                 */
                long numberOfNights =
                        java.time.temporal.ChronoUnit.DAYS.between(
                                checkIn.toLocalDate(),
                                checkOut.toLocalDate());


                if (numberOfNights <= 0) {

                    throw new SQLException(
                            "Invalid reservation dates.");
                }


                /*
                 * =================================================
                 * STEP 6
                 * Calculate additional upgrade amount.
                 * =================================================
                 */
                double priceDifference =
                        newRoomPrice - currentPrice;

                double additionalAmount =
                        priceDifference * numberOfNights;


                if (additionalAmount <= 0) {

                    throw new SQLException(
                            "Upgrade amount must be greater than zero.");
                }


                /*
                 * =================================================
                 * STEP 7
                 * Insert NEW upgrade payment.
                 *
                 * Original payment is NOT changed.
                 * =================================================
                 */
                String paymentSql =
                    "INSERT INTO PAYMENTS " +
                    "(RESERVATION_ID, PAYMENT_METHOD, AMOUNT, " +
                    "PAYMENT_STATUS, PAYMENT_DATE) " +
                    "VALUES (?, ?, ?, 'SUCCESS', SYSDATE)";

                int paymentId;

                try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    paymentSql,
                                    new String[] {"PAYMENT_ID"})
                ) {

                    statement.setInt(
                            1,
                            reservationId);

                    statement.setString(
                            2,
                            paymentMethod);

                    statement.setDouble(
                            3,
                            additionalAmount);

                    int rowsInserted =
                            statement.executeUpdate();

                    if (rowsInserted == 0) {

                        throw new SQLException(
                                "Upgrade payment could not be created.");
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
                                    "Upgrade payment ID could not " +
                                    "be generated.");
                        }
                    }
                }


                /*
                 * =================================================
                 * STEP 8
                 * Change the existing reservation to the new room.
                 *
                 * IMPORTANT:
                 * We do NOT create another reservation.
                 * =================================================
                 */
                String updateReservationSql =
                    "UPDATE RESERVATIONS " +
                    "SET ROOM_ID = ? " +
                    "WHERE RESERVATION_ID = ? " +
                    "AND STATUS = 'BOOKED'";

                try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    updateReservationSql)
                ) {

                    statement.setInt(
                            1,
                            newRoomId);

                    statement.setInt(
                            2,
                            reservationId);

                    int rowsUpdated =
                            statement.executeUpdate();

                    if (rowsUpdated == 0) {

                        throw new SQLException(
                                "Reservation room could not be updated.");
                    }
                }


                /*
                 * =================================================
                 * STEP 9
                 * Everything succeeded.
                 * =================================================
                 */
                connection.commit();

                return paymentId;


            } catch (SQLException e) {

                /*
                 * Something failed.
                 *
                 * Undo:
                 * - payment INSERT
                 * - reservation ROOM_ID update
                 */
                connection.rollback();

                throw new RuntimeException(
                        "Room upgrade transaction failed. " +
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
