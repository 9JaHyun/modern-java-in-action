package _05_CompletableFuture.shop.improvedShop;

import _05_CompletableFuture.shop.improvedShop.Discount.Code;

public class Quote {

    private final String shopName;
    private final double price;
    private final Code discountCode;

    public Quote(String shopName, double price,
          Code discountCode) {
        this.shopName = shopName;
        this.price = price;
        this.discountCode = discountCode;
    }

    public static Quote parse(String s) {
        String[] split = s.split(":");
        String shopName = split[0];
        double price = Double.parseDouble(split[1]);
        Code discountCode = Code.valueOf(split[2]);
        return new Quote(shopName, price, discountCode);
    }

    public String getShopName() {
        return shopName;
    }

    public double getPrice() {
        return price;
    }

    public Code getDiscountCode() {
        return discountCode;
    }
}
