package _05_CompletableFuture.shop;

import java.util.Random;

public class BeforeExample {

    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public double getPrice(String product) {
        return calculatePrice(product);
    }

    public double calculatePrice(String product) {
        delay();
        return new Random().nextDouble() * product.charAt(0) + product.charAt(1);
    }

}
