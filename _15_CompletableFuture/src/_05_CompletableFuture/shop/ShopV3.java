package _05_CompletableFuture.shop;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

// CompletableFuture 를 new 생성자가 아닌 정적 팩토리 메서드로 만들자
public class ShopV3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ShopV3 shopV3 = new ShopV3();
        long start = System.nanoTime();
        Future<Double> futurePrice = shopV3.getPriceAsync("product1");
        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("InvocationTime: " + invocationTime + "msecs");

        try {
            Double price = futurePrice.get();   // 정상적인 값을 반환할때까지 계속 대기
            System.out.printf("Price is %.2f%n", price);
            System.out.println("after successfully get: " + futurePrice.isDone());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Price returned after " + retrievalTime + " msecs");
    }

    private static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 이 경우엔 기본적으로 completeExceptionally 지원
    private Future<Double> getPriceAsync(String product) {
        // Default: ForkJoinPool 의 Executor 중 하나가 Supplier 실행
        // 오버로드된 메서드를 통해 Executor을 직접 선택 가능
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
//        return CompletableFuture.supplyAsync(() -> sendError(product));
    }

    private double calculatePrice(String product) {
        delay();
        return new Random().nextDouble() * product.charAt(0) + product.charAt(1);
    }

    private double sendError(String product) {
        throw new RuntimeException();
    }

}
