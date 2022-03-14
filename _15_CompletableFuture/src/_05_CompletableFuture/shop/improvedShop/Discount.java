package _05_CompletableFuture.shop.improvedShop;

import java.util.Random;

public class Discount {

    private static Random random = new Random();

    public enum Code {
        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

        private final int discountPercent;

        Code(int discountPercent) {
            this.discountPercent = discountPercent;
        }
    }

    private static void delay() {
        try {
            Thread.sleep(5000L);
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

    public static String applyDiscount(Quote quote) {
        return "%s's %s price is %f".formatted(quote.getShopName(), quote.getProduct(),
              apply(quote.getPrice(), quote.getDiscountCode()));
    }

    private static double apply(double price, Code code) {
        randomDelay();
        return price * (100 - code.discountPercent) / 100;
    }
}
