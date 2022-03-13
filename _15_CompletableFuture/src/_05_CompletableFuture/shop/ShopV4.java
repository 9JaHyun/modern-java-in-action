package _05_CompletableFuture.shop;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

// 이때까지는 블록 코드였으나 이번에는 비블록 코드를 만들어보자.
public class ShopV4 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Shop> shops = Arrays.asList(new Shop("shop1"), new Shop("shop2"),
              new Shop("shop3"), new Shop("shop4"), new Shop("shop5"), new Shop("shop3"),
              new Shop("shop4"), new Shop("shop5"), new Shop("shop6"), new Shop("shop7"),
              new Shop("shop8"), new Shop("shop9"), new Shop("shop10"),
              new Shop("shop11"), new Shop("shop12"), new Shop("shop13"), new Shop("shop14"),
              new Shop("shop15"), new Shop("shop16"), new Shop("shop17"), new Shop("shop18"),
              new Shop("shop19"));
        long start = System.nanoTime();
//        System.out.println(new ShopV4().findPricesV1(shops, "product1")); // delay(1000) 생각하면, 병렬성 지원 X
//        System.out.println(new ShopV4().findPricesV2(shops, "product1"));
//        System.out.println(new ShopV4().findPricesV3(shops, "product1"));
        System.out.println(new ShopV4().findPricesV4(shops, "product1"));
        long runningTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("RunningTime: " + runningTime + "msecs");
    }

    private List<String> findPricesV1(List<Shop> shops, String product) {
        return shops.stream()
              .map(shop -> "%s price is %.2f".formatted(shop.getName(), shop.getPrice(product)))
              .collect(Collectors.toList());
    }

    // 최적화1. parallelStream 사용 (동기호출)
    private List<String> findPricesV2(List<Shop> shops, String product) {
        return shops.parallelStream()
              .map(shop -> "%s price is %.2f".formatted(shop.getName(), shop.getPrice(product)))
              .collect(Collectors.toList());
    }

    // 최적화2. CompletableFuture 사용
    // 빠르긴한데... 획기적이진 않은데?? (but Executor을 선택해서 최적화 할 수 있다는 점이 중요!!)
    private List<String> findPricesV3(List<Shop> shops, String product) {
        // 주의점.. 이렇게 하나의 큰 파이프라인을 형성하는 것이 아니라 두 파이프라인으로 나누자
//        return shops.stream()
//              .map(shop -> CompletableFuture.supplyAsync(
//                    () -> "%s price is %.2f".formatted(shop.getName(), shop.getPrice(product))))
//              .map(CompletableFuture::join)
//              .collect(Collectors.toList());

        List<CompletableFuture<String>> priceFutures = shops.stream()
              .map(shop -> CompletableFuture.supplyAsync(
                    () -> "%s price is %.2f".formatted(shop.getName(), shop.getPrice(product))))
              .collect(Collectors.toList());

        // 하나의 파이프라인이 아닌 두 파이프라인으로 나누기 (지연 로딩 특성때문에...)
        return priceFutures.stream()
              .map(CompletableFuture::join) // 모든 비동기 동작이 끝나기 기다림. (get()과 같으나 예외 발생X)
              .collect(Collectors.toList());
    }

    // Custom Executor 선택해서 최적화
    private List<String> findPricesV4(List<Shop> shops, String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
              .map(shop -> CompletableFuture.supplyAsync(
                          () -> "%s price is %.2f".formatted(shop.getName(), shop.getPrice(product)),
                          makeCustomExecutor(shops)))
                    .collect(Collectors.toList());

        // 하나의 파이프라인이 아닌 두 파이프라인으로 나누기 (지연 로딩 특성때문에...)
        return priceFutures.stream()
              .map(CompletableFuture::join) // 모든 비동기 동작이 끝나기 기다림. (get()과 같으나 예외 발생X)
              .collect(Collectors.toList());
    }

    // findPricesV3의 장점 (Custom Executor 사용)
    private Executor makeCustomExecutor(List list) {
        // 최대 100개까지 동적으로 스레드 생성
        return Executors.newFixedThreadPool(Math.min(list.size(), 100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                // Daemon Thread
                // 일반적으로는 스레드가 실행 중이면 자바 프로그램은 종료가 안됨
                //  그래서 이벤트를 기다리느라 종료되지 않는 스레드가 있으면 문제 발생
                //  그래서 자바 프로그램이 종료될 떄 강제로 종료할 수 있는 스레드가 필요
                // 성능도 같아서 안정성을 고려해서 선택하면 된다.
                t.setDaemon(true); // 프로그램 종료 방해하지 않는 데몬 스레드 사용
                return t;
            }
        });
    }
}
class Shop {

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

    public double getPrice(String product) {
        return calculatePrice(product);
    }

    public double calculatePrice(String product) {
        delay();
        return new Random().nextDouble() * product.charAt(0) + product.charAt(1);
    }
}