package com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hotel.DBConnection;
import com.hotel.model.Payment;

public class PaymentDAO {

    public int createPayment(Payment payment) {

        String sql =
            "INSERT INTO PAYMENTS " +
            "(RESERVATION_ID, PAYMENT_METHOD, AMOUNT, " +
            "PAYMENT_STATUS, PAYMENT_DATE) " +
            "VALUES (?, ?, ?, ?, SYSDATE)";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(
                            sql,
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

            if (rowsInserted > 0) {

                try (ResultSet generatedKeys =
                        statement.getGeneratedKeys()) {

                    if (generatedKeys.next()) {

                        return generatedKeys.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error creating payment.", e);
        }

        return 0;
    }

    public Payment findById(int paymentId) {

        String sql =
            "SELECT PAYMENT_ID, RESERVATION_ID, " +
            "PAYMENT_METHOD, AMOUNT, PAYMENT_STATUS, " +
            "PAYMENT_DATE " +
            "FROM PAYMENTS " +
            "WHERE PAYMENT_ID = ?";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    paymentId);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                Payment payment =
                        new Payment();

                payment.setPaymentId(
                        resultSet.getInt("PAYMENT_ID"));

                payment.setReservationId(
                        resultSet.getInt("RESERVATION_ID"));

                payment.setPaymentMethod(
                        resultSet.getString("PAYMENT_METHOD"));

                payment.setAmount(
                        resultSet.getDouble("AMOUNT"));

                payment.setPaymentStatus(
                        resultSet.getString("PAYMENT_STATUS"));

                if (resultSet.getTimestamp(
                        "PAYMENT_DATE") != null) {

                    payment.setPaymentDate(
                            resultSet.getTimestamp(
                                    "PAYMENT_DATE")
                                    .toLocalDateTime());
                }

                return payment;
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error finding payment.", e);
        }

        return null;
    }

    public Payment findByReservationId(
            int reservationId) {

        String sql =
            "SELECT PAYMENT_ID, RESERVATION_ID, " +
            "PAYMENT_METHOD, AMOUNT, PAYMENT_STATUS, " +
            "PAYMENT_DATE " +
            "FROM PAYMENTS " +
            "WHERE RESERVATION_ID = ?";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    reservationId);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                Payment payment =
                        new Payment();

                payment.setPaymentId(
                        resultSet.getInt("PAYMENT_ID"));

                payment.setReservationId(
                        resultSet.getInt("RESERVATION_ID"));

                payment.setPaymentMethod(
                        resultSet.getString("PAYMENT_METHOD"));

                payment.setAmount(
                        resultSet.getDouble("AMOUNT"));

                payment.setPaymentStatus(
                        resultSet.getString("PAYMENT_STATUS"));

                if (resultSet.getTimestamp(
                        "PAYMENT_DATE") != null) {

                    payment.setPaymentDate(
                            resultSet.getTimestamp(
                                    "PAYMENT_DATE")
                                    .toLocalDateTime());
                }

                return payment;
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error finding payment by reservation.",
                    e);
        }

        return null;
    }
}
