package main.java.structures.adapter;

import java.util.Date;

public class StandardReservationAdapter implements IReservable {
    private Reservable resSystem;

    public StandardReservationAdapter(Reservable baseImpl) {
        this.resSystem = baseImpl;
    }

    @Override
    public String getHotelInfo(String id) {
        return resSystem.getHotelInfo(id);
    }

    @Override
    public String makeReservation(Date from, int numOfDays, String id, String client) {
        long code = resSystem.makeReservation(id, client, from, numOfDays);
        return String.valueOf(code);
    }

    @Override
    public String getReservation(String code) {
        long longCode = Long.parseLong(code);
        return resSystem.getReservationData(longCode);
    }
    
}
