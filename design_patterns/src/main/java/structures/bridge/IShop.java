package main.java.structures.bridge;

public interface IShop {
    String describe(Product product);
    void buyProduct(Product product, int quantity);
    void buyBox(Product product, int itemsPerBox);
}
