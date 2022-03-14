package _05_CompletableFuture.shop.improvedShop;

import _05_CompletableFuture.shop.improvedShop.Discount.Code;
import java.util.Random;

public class Shop {

    private String name;
    private static Random random = new Random();

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

    // ShopV6을 위한 randomDelay()
    private static void randomDelay() {
        int delayTime = 500 + random.nextInt(2000);
        try {
            Thread.sleep(delayTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public String getPrice(String shopName, String product) {
        double price = calculatePrice(product);
        Code code = Code.values()[new Random().nextInt(Code.values().length)];
        return "%s:%s:%.2f:%s".formatted(shopName, product, price, code);
    }

    private double calculatePrice(String product) {
        randomDelay();
        return new Random().nextDouble() * product.charAt(0) + product.charAt(1);
    }
}