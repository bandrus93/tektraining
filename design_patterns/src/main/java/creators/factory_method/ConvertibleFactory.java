package main.java.creators.factory_method;

public class ConvertibleFactory extends AutomobileFactory {

    @Override
    protected Automobile assembleVehicle() {
        return new ConvertibleCar();
    }
    
}
