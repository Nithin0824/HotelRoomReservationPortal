package com.hotel.service;

import com.hotel.dao.ReservationDAO;
import com.hotel.dao.ReservationDAO.RoomDetails;
import com.hotel.model.Reservation;

public class ReservationService {

    private ReservationDAO reservationDAO;

    public ReservationService() {
        reservationDAO = new ReservationDAO();
    }


    public int createReservation(
            Reservation reservation) {

        return reservationDAO.createReservation(
                reservation);
    }


    public Reservation findById(
            int reservationId) {

        return reservationDAO.findById(
                reservationId);
    }


    public boolean updateStatus(
            int reservationId,
            String status) {

        return reservationDAO.updateStatus(
                reservationId,
                status);
    }


    public boolean cancelReservation(
            int reservationId) {

        return reservationDAO.cancelReservation(
                reservationId);
    }


    public boolean isRoomBooked(
            int roomId,
            String checkIn,
            String checkOut) {

        return reservationDAO.isRoomBooked(
                roomId,
                checkIn,
                checkOut);
    }


    public RoomDetails findRoomDetails(
            int roomId) {

        return reservationDAO.findRoomDetails(
                roomId);
    }
}