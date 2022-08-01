package main.java.structures.decorator;

public class Decaf implements Coffee {

    @Override
    public String getDescription() {
        return "de-clawed";
    }

    @Override
    public int getPrice() {
        return 150;
    }
    
}
