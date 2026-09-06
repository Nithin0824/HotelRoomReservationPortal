package com.hotel.model;

public class Room {

    private int roomId;
    private String roomNumber;
    private String typeName;
    private double basePrice;
    private String status;

    public Room() {
    }

    public Room(int roomId, String roomNumber, String typeName,
                double basePrice, String status) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.typeName = typeName;
        this.basePrice = basePrice;
        this.status = status;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
