package com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hotel.DBConnection;
import com.hotel.model.Reservation;

public class ReservationDAO {

    public int createReservation(Reservation reservation) {

        String sql =
            "INSERT INTO RESERVATIONS " +
            "(ROOM_ID, GUEST_NAME, CHECK_IN, CHECK_OUT, GUESTS, STATUS) " +
            "VALUES (?, ?, TO_DATE(?, 'YYYY-MM-DD'), " +
            "TO_DATE(?, 'YYYY-MM-DD'), ?, ?)";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(
                            sql,
                            new String[] {"RESERVATION_ID"})
        ) {

            statement.setInt(
                    1,
                    reservation.getRoomId());

            statement.setString(
                    2,
                    reservation.getGuestName());

            statement.setString(
                    3,
                    reservation.getCheckIn().toString());

            statement.setString(
                    4,
                    reservation.getCheckOut().toString());

            statement.setInt(
                    5,
                    reservation.getGuests());

            statement.setString(
                    6,
                    reservation.getStatus());

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
                    "Error creating reservation.", e);
        }

        return 0;
    }


    public Reservation findById(int reservationId) {

        String sql =
            "SELECT RESERVATION_ID, ROOM_ID, " +
            "GUEST_NAME, CHECK_IN, CHECK_OUT, " +
            "GUESTS, STATUS " +
            "FROM RESERVATIONS " +
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

                Reservation reservation =
                        new Reservation();

                reservation.setReservationId(
                        resultSet.getInt("RESERVATION_ID"));

                reservation.setRoomId(
                        resultSet.getInt("ROOM_ID"));

                reservation.setGuestName(
                        resultSet.getString("GUEST_NAME"));

                reservation.setCheckIn(
                        resultSet.getDate("CHECK_IN")
                                .toLocalDate());

                reservation.setCheckOut(
                        resultSet.getDate("CHECK_OUT")
                                .toLocalDate());

                reservation.setGuests(
                        resultSet.getInt("GUESTS"));

                reservation.setStatus(
                        resultSet.getString("STATUS"));

                return reservation;
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error finding reservation.", e);
        }

        return null;
    }


    public boolean updateStatus(
            int reservationId,
            String status) {

        String sql =
            "UPDATE RESERVATIONS " +
            "SET STATUS = ? " +
            "WHERE RESERVATION_ID = ?";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    status);

            statement.setInt(
                    2,
                    reservationId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error updating reservation status.", e);
        }
    }


    public boolean cancelReservation(int reservationId) {

        String sql =
            "UPDATE RESERVATIONS " +
            "SET STATUS = 'CANCELLED' " +
            "WHERE RESERVATION_ID = ? " +
            "AND STATUS = 'BOOKED'";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    reservationId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error cancelling reservation.", e);
        }
    }


    public RoomDetails findRoomDetails(int roomId) {

        String sql =
            "SELECT r.ROOM_NUMBER, " +
            "rt.TYPE_NAME, rt.BASE_PRICE " +
            "FROM HOTEL_ROOMS r " +
            "JOIN ROOM_TYPES rt ON r.TYPE_ID = rt.TYPE_ID " +
            "WHERE r.ROOM_ID = ?";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    roomId);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return new RoomDetails(
                        resultSet.getInt("ROOM_NUMBER"),
                        resultSet.getString("TYPE_NAME"),
                        resultSet.getDouble("BASE_PRICE")
                );
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error finding room details.", e);
        }

        return null;
    }


    public boolean isRoomBooked(
            int roomId,
            String checkIn,
            String checkOut) {

        String sql =
            "SELECT COUNT(*) " +
            "FROM RESERVATIONS " +
            "WHERE ROOM_ID = ? " +
            "AND STATUS = 'BOOKED' " +
            "AND CHECK_IN < TO_DATE(?, 'YYYY-MM-DD') " +
            "AND CHECK_OUT > TO_DATE(?, 'YYYY-MM-DD')";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    roomId);

            statement.setString(
                    2,
                    checkOut);

            statement.setString(
                    3,
                    checkIn);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return resultSet.getInt(1) > 0;
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error checking room availability.", e);
        }

        return false;
    }


    public static class RoomDetails {

        private int roomNumber;
        private String typeName;
        private double price;

        public RoomDetails(
                int roomNumber,
                String typeName,
                double price) {

            this.roomNumber = roomNumber;
            this.typeName = typeName;
            this.price = price;
        }

        public int getRoomNumber() {
            return roomNumber;
        }

        public String getTypeName() {
            return typeName;
        }

        public double getPrice() {
            return price;
        }
    }
}