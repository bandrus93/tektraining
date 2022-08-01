package main.java.structures.adapter;

import java.util.Date;

public interface IReservable {
    String getHotelInfo(String id);
    String makeReservation(Date from, int numOfDays, String id, String client);
    String getReservation(String code);
}
