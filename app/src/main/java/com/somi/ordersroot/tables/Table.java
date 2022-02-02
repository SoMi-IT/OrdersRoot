package com.somi.ordersroot.tables;

public class Table {


    private int tableNumber;
    private int availableSeats;
    private int occupiedSeats;
    private String room;
    private boolean isReserved;
    private boolean isOutside;


    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void setOccupiedSeats(int occupiedSeats) { this.occupiedSeats = occupiedSeats; }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public void setOutside(boolean outside) {
        isOutside = outside;
    }


    public int getTableNumber() { return tableNumber; }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public int getOccupiedSeats() {
        return occupiedSeats;
    }

    public String getRoom() {
        return room;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public boolean isOutside() {
        return isOutside;
    }

}
