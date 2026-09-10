package com.hotel.service;

import java.util.List;

import com.hotel.dao.RoomDAO;
import com.hotel.model.Room;

public class RoomService {

    private RoomDAO roomDAO;

    public RoomService() {
        roomDAO = new RoomDAO();
    }


    // Customer: find available rooms for selected dates and guest capacity
    public List<Room> findAvailableRooms(
            String checkIn,
            String checkOut,
            int guestCount) {

        return roomDAO.findAvailableRooms(
                checkIn,
                checkOut,
                guestCount);
    }


    // Customer: find available higher-priced rooms for upgrade
    public List<Room> findUpgradeRooms(
            int currentRoomId,
            String checkIn,
            String checkOut) {

        return roomDAO.findUpgradeRooms(
                currentRoomId,
                checkIn,
                checkOut);
    }


    // Admin: find all rooms
    public List<Room> findAllRooms() {

        return roomDAO.findAllRooms();
    }


    // Admin: find one room by ID
    public Room findRoomById(
            int roomId) {

        return roomDAO.findRoomById(
                roomId);
    }


    // Admin: add room
    public boolean addRoom(
            int roomNumber,
            int typeId,
            String status) {

        return roomDAO.addRoom(
                roomNumber,
                typeId,
                status);
    }


    // Admin: update room
    public boolean updateRoom(
            int roomId,
            int roomNumber,
            int typeId,
            String status) {

        return roomDAO.updateRoom(
                roomId,
                roomNumber,
                typeId,
                status);
    }


    // Admin: update room availability
    public boolean updateRoomStatus(
            int roomId,
            String status) {

        return roomDAO.updateRoomStatus(
                roomId,
                status);
    }
}
