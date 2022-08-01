package main.java.creators.factory_method;

public class CoupeFactory extends AutomobileFactory {

    @Override
    protected Automobile assembleVehicle() {
        return new CoupeCar();
    }
    
}
