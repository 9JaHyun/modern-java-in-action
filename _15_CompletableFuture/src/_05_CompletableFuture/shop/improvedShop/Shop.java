package _05_CompletableFuture.shop.improvedShop;

import _05_CompletableFuture.shop.improvedShop.Discount.Code;
import java.util.Random;

public class Shop {

    private String name;

    public Shop(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getPrice(String product) {
        double price = calculatePrice(product);
        Code code = Code.values()[new Random().nextInt(Code.values().length)];
        return "%s:%.2f:%s".formatted(product, price, code);
    }

    private double calculatePrice(String product) {
        delay();
        return new Random().nextDouble() * product.charAt(0) + product.charAt(1);
    }
}