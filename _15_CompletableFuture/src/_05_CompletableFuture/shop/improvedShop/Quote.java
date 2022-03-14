package _05_CompletableFuture.shop.improvedShop;

import _05_CompletableFuture.shop.improvedShop.Discount.Code;

public class Quote {

    private final String shopName;
    private final String product;
    private final double price;
    private final Code discountCode;

    public Quote(String shopName, String product, double price,
          Code discountCode) {
        this.shopName = shopName;
        this.product = product;
        this.price = price;
        this.discountCode = discountCode;
    }

    public static Quote parse(String s) {
        String[] split = s.split(":");
        String shopName = split[0];
        String product = split[1];
        double price = Double.parseDouble(split[2]);
        Code discountCode = Code.valueOf(split[3]);
        return new Quote(shopName, product, price, discountCode);
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

    public String getProduct() {
        return product;
    }
}
