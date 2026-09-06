package com.hotel.service;

import java.util.List;

import com.hotel.dao.RoomDAO;
import com.hotel.model.Room;

public class RoomService {

    private RoomDAO roomDAO;

    public RoomService() {
        roomDAO = new RoomDAO();
    }

    public List<Room> findAvailableRooms(
            String checkIn,
            String checkOut) {

        return roomDAO.findAvailableRooms(
                checkIn,
                checkOut);
    }
}
