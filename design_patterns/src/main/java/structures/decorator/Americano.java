package main.java.structures.decorator;

public class Americano implements Coffee {

    @Override
    public String getDescription() {
        return "Americcino";
    }

    @Override
    public int getPrice() {
        return 200;
    }
    
}
