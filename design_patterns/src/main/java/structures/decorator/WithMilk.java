package main.java.structures.decorator;

public class WithMilk implements Coffee {
    private Coffee coffee;

    public WithMilk(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + " with milk";
    }

    @Override
    public int getPrice() {
        return coffee.getPrice() + 20;
    }
    
}
