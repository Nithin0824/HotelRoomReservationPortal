
package com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hotel.DBConnection;
import com.hotel.model.Room;

public class RoomDAO {

    // Customer: find available rooms for selected dates and guest capacity
    public List<Room> findAvailableRooms(
            String checkIn,
            String checkOut,
            int guestCount) {

        List<Room> rooms = new ArrayList<>();

        String sql =
            "SELECT r.ROOM_ID, r.ROOM_NUMBER, " +
            "rt.TYPE_NAME, rt.BASE_PRICE, r.STATUS " +
            "FROM HOTEL_ROOMS r " +
            "JOIN ROOM_TYPES rt " +
            "ON r.TYPE_ID = rt.TYPE_ID " +
            "WHERE r.STATUS = 'AVAILABLE' " +
            "AND rt.CAPACITY >= ? " +
            "AND NOT EXISTS ( " +
            "    SELECT 1 " +
            "    FROM RESERVATIONS res " +
            "    WHERE res.ROOM_ID = r.ROOM_ID " +
            "    AND res.STATUS = 'BOOKED' " +
            "    AND TO_DATE(?, 'YYYY-MM-DD') < res.CHECK_OUT " +
            "    AND TO_DATE(?, 'YYYY-MM-DD') > res.CHECK_IN " +
            ") " +
            "ORDER BY r.ROOM_ID";

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            // 1. Minimum required capacity
            statement.setInt(1, guestCount);

            // 2. Check-in date
            statement.setString(2, checkIn);

            // 3. Check-out date
            statement.setString(3, checkOut);

            try (
                ResultSet resultSet =
                        statement.executeQuery()
            ) {

                while (resultSet.next()) {

                    Room room = new Room();

                    room.setRoomId(
                            resultSet.getInt("ROOM_ID"));

                    room.setRoomNumber(
                            resultSet.getString("ROOM_NUMBER"));

                    room.setTypeName(
                            resultSet.getString("TYPE_NAME"));

                    room.setBasePrice(
                            resultSet.getDouble("BASE_PRICE"));

                    room.setStatus(
                            resultSet.getString("STATUS"));

                    rooms.add(room);
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error loading available rooms.", e);
        }

        return rooms;
    }


    // Admin: find all rooms
    public List<Room> findAllRooms() {

        List<Room> rooms = new ArrayList<>();

        String sql =
            "SELECT r.ROOM_ID, r.ROOM_NUMBER, " +
            "rt.TYPE_NAME, rt.BASE_PRICE, r.STATUS " +
            "FROM HOTEL_ROOMS r " +
            "JOIN ROOM_TYPES rt " +
            "ON r.TYPE_ID = rt.TYPE_ID " +
            "ORDER BY r.ROOM_NUMBER";

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet resultSet =
                    statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Room room = new Room();

                room.setRoomId(
                        resultSet.getInt("ROOM_ID"));

                room.setRoomNumber(
                        resultSet.getString("ROOM_NUMBER"));

                room.setTypeName(
                        resultSet.getString("TYPE_NAME"));

                room.setBasePrice(
                        resultSet.getDouble("BASE_PRICE"));

                room.setStatus(
                        resultSet.getString("STATUS"));

                rooms.add(room);
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error loading room inventory.", e);
        }

        return rooms;
    }


    // Customer: find available rooms that are higher-priced than current room
    public List<Room> findUpgradeRooms(
            int currentRoomId,
            String checkIn,
            String checkOut) {

        List<Room> rooms = new ArrayList<>();

        String sql =
            "SELECT r.ROOM_ID, r.ROOM_NUMBER, " +
            "rt.TYPE_NAME, rt.BASE_PRICE, r.STATUS " +
            "FROM HOTEL_ROOMS r " +
            "JOIN ROOM_TYPES rt " +
            "ON r.TYPE_ID = rt.TYPE_ID " +
            "WHERE r.STATUS = 'AVAILABLE' " +

            // Only rooms more expensive than current room
            "AND rt.BASE_PRICE > ( " +
            "    SELECT current_rt.BASE_PRICE " +
            "    FROM HOTEL_ROOMS current_r " +
            "    JOIN ROOM_TYPES current_rt " +
            "    ON current_r.TYPE_ID = current_rt.TYPE_ID " +
            "    WHERE current_r.ROOM_ID = ? " +
            ") " +

            // Exclude rooms already booked for the reservation dates
            "AND NOT EXISTS ( " +
            "    SELECT 1 " +
            "    FROM RESERVATIONS res " +
            "    WHERE res.ROOM_ID = r.ROOM_ID " +
            "    AND res.STATUS = 'BOOKED' " +
            "    AND TO_DATE(?, 'YYYY-MM-DD') < res.CHECK_OUT " +
            "    AND TO_DATE(?, 'YYYY-MM-DD') > res.CHECK_IN " +
            ") " +

            "ORDER BY rt.BASE_PRICE ASC, r.ROOM_NUMBER ASC";

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            // Parameter 1: current room ID
            statement.setInt(
                    1,
                    currentRoomId);

            // Parameter 2: check-out date
            statement.setString(
                    2,
                    checkOut);

            // Parameter 3: check-in date
            statement.setString(
                    3,
                    checkIn);

            try (
                ResultSet resultSet =
                        statement.executeQuery()
            ) {

                while (resultSet.next()) {

                    Room room = new Room();

                    room.setRoomId(
                            resultSet.getInt("ROOM_ID"));

                    room.setRoomNumber(
                            resultSet.getString("ROOM_NUMBER"));

                    room.setTypeName(
                            resultSet.getString("TYPE_NAME"));

                    room.setBasePrice(
                            resultSet.getDouble("BASE_PRICE"));

                    room.setStatus(
                            resultSet.getString("STATUS"));

                    rooms.add(room);
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error loading upgrade rooms.", e);
        }

        return rooms;
    }


    // Admin: find one room by ID
    public Room findRoomById(int roomId) {

        Room room = null;

        String sql =
            "SELECT r.ROOM_ID, r.ROOM_NUMBER, " +
            "r.TYPE_ID, rt.TYPE_NAME, rt.BASE_PRICE, r.STATUS " +
            "FROM HOTEL_ROOMS r " +
            "JOIN ROOM_TYPES rt " +
            "ON r.TYPE_ID = rt.TYPE_ID " +
            "WHERE r.ROOM_ID = ?";

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    roomId);

            try (
                ResultSet resultSet =
                        statement.executeQuery()
            ) {

                if (resultSet.next()) {

                    room = new Room();

                    room.setRoomId(
                            resultSet.getInt("ROOM_ID"));

                    room.setRoomNumber(
                            resultSet.getString("ROOM_NUMBER"));

                    room.setTypeName(
                            resultSet.getString("TYPE_NAME"));

                    room.setBasePrice(
                            resultSet.getDouble("BASE_PRICE"));

                    room.setStatus(
                            resultSet.getString("STATUS"));
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error loading room by ID.", e);
        }

        return room;
    }


    // Admin: add a new room
    public boolean addRoom(
            int roomNumber,
            int typeId,
            String status) {

        String sql =
            "INSERT INTO HOTEL_ROOMS " +
            "(ROOM_NUMBER, TYPE_ID, STATUS) " +
            "VALUES (?, ?, ?)";

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    roomNumber);

            statement.setInt(
                    2,
                    typeId);

            statement.setString(
                    3,
                    status);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error adding room.", e);
        }
    }


    // Admin: update room details
    public boolean updateRoom(
            int roomId,
            int roomNumber,
            int typeId,
            String status) {

        String sql =
            "UPDATE HOTEL_ROOMS " +
            "SET ROOM_NUMBER = ?, " +
            "    TYPE_ID = ?, " +
            "    STATUS = ? " +
            "WHERE ROOM_ID = ?";

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    roomNumber);

            statement.setInt(
                    2,
                    typeId);

            statement.setString(
                    3,
                    status);

            statement.setInt(
                    4,
                    roomId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error updating room.", e);
        }
    }


    // Admin: update only room availability/status
    public boolean updateRoomStatus(
            int roomId,
            String status) {

        String sql =
            "UPDATE HOTEL_ROOMS " +
            "SET STATUS = ? " +
            "WHERE ROOM_ID = ?";

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    status);

            statement.setInt(
                    2,
                    roomId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error updating room status.", e);
        }
    }

}
