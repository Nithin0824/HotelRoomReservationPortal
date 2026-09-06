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

    public List<Room> findAvailableRooms(
            String checkIn,
            String checkOut) {

        List<Room> rooms = new ArrayList<>();

        String sql =
            "SELECT r.ROOM_ID, r.ROOM_NUMBER, " +
            "rt.TYPE_NAME, rt.BASE_PRICE, r.STATUS " +
            "FROM HOTEL_ROOMS r " +
            "JOIN ROOM_TYPES rt ON r.TYPE_ID = rt.TYPE_ID " +
            "WHERE r.STATUS = 'AVAILABLE' " +
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
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(1, checkIn);
            statement.setString(2, checkOut);

            ResultSet resultSet = statement.executeQuery();

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
                    "Error loading available rooms.", e);
        }

        return rooms;
    }
}
