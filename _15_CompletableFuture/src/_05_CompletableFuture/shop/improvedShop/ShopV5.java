package improvedShop;

import _05_CompletableFuture.shop.improvedShop.Discount;
import _05_CompletableFuture.shop.improvedShop.Quote;
import _05_CompletableFuture.shop.improvedShop.Shop;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

// 이번에는 V4에서 한 일회성 작업을 선언형으로 변경해 파이프라인화 하자
public class ShopV5 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Shop> shops = Arrays.asList(new Shop("shop1"), new Shop("shop2"),
              new Shop("shop3"), new Shop("shop4"), new Shop("shop5"),
              new Shop("shop6"), new Shop("shop7"), new Shop("shop8"),
              new Shop("shop9"), new Shop("shop10"));
        long start = System.nanoTime();
//        System.out.println(new ShopV5().findPricesV1(shops, "product1"));
        System.out.println(new ShopV5().findPricesV2(shops, "product1"));
        long runningTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("RunningTime: " + runningTime + "msecs");
    }

    // 순차적 방식으로 처리
    private List<String> findPricesV1(List<Shop> shops, String product) {
        return shops.stream()
              .map(shop -> shop.getPrice(product))
              .map(Quote::parse)
              .map(Discount::applyDiscount)
              .collect(Collectors.toList());
    }

    // 동기 작업을 추가해주자 v1과 10배 차이
    private List<String> findPricesV2(List<Shop> shops, String product) {
        List<CompletableFuture<String>> futures = shops.stream()
              .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product),
                    makeCustomExecutor(shops)))
              // thenApply() : 여기서는 굳이 원격 서비스나 I/O가 없기 때문에 지연 없이 동작 수행
              // 그래서 딱히 CompletableFuture가 끝날 때까지 블록하는 작업이 없는 thenApply() 사용
              // thenApply()는 map()같은 메서드 -> 기존의 CompletableFuture 객체를 그대로 사용.
              .map(future -> future.thenApply(Quote::parse))
              // thenCompose() : 원격 Discount 서비스에서 제공하는 할인율을 적용해야 함.
              // 그래서 동기적으로 작업을 수행해야 함. ==> (Quote로 변환, 최종 가격 획득)
              // flatMap() 같은 메서드 -> 새로운 CompletableFuture 객체를 한번 더 생성해서 사용.
              .map(future -> future.thenCompose(
                    quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote))))
              .collect(Collectors.toList());

        return futures.stream()
              .map(CompletableFuture::join)
              .collect(Collectors.toList());
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

