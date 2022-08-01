package main.java.structures.adapter;

import java.util.Date;

public interface Reservable {
    String getHotelInfo(String id);
    long makeReservation(String id, String client, Date from, int numOfDays);
    String getReservationData(long code);
}
