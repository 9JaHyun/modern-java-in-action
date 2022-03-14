package _05_CompletableFuture.shop.improvedShop;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// 이번에는 결과가 나오는대로 고객에게 보내는 기능을 추가.

public class ShopV6 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Shop> shops = Arrays.asList(new Shop("shop1"), new Shop("shop2"),
              new Shop("shop3"), new Shop("shop4"), new Shop("shop5"),
              new Shop("shop6"), new Shop("shop7"), new Shop("shop8"),
              new Shop("shop9"), new Shop("shop10"));
        long start = System.nanoTime();
        new ShopV6().showPrices(shops, "product1");
        long runningTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("RunningTime: " + runningTime + "msecs");
    }

    public void showPrices(List<Shop> shops, String product) {
        CompletableFuture[] futures = findPriceStream(shops, product)
              .map(f -> f.thenAccept(System.out::println))
              .toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futures).join();
    }

    private Stream<CompletableFuture<String>> findPriceStream(List<Shop> shops, String product) {
        return shops.stream()
              .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(shop.getName(), product),
                    makeCustomExecutor(shops)))
              .map(future -> future.thenApply(Quote::parse))
              .map(future -> future.thenCompose(
                    quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote),
                          makeCustomExecutor(shops))));
    }

    private Executor makeCustomExecutor(List list) {
        return Executors.newFixedThreadPool(Math.min(list.size(), 100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        });
    }
}

