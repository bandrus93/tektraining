package main.java.structures.decorator;

public class Espresso implements Coffee {

    @Override
    public String getDescription() {
        return "espresso shot";
    }

    @Override
    public int getPrice() {
        return 190;
    }
    
}
