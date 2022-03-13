package _05_CompletableFuture.shop;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

// 여기서 문제는.. Thread에 대한 예외처리가 제대로 되어 있지 않다는 것.
// getPriceAsync() Thread 내부에서 만약 에러가 발생한다면? => get() 결과가 나올때까지 영원히 버팀.
public class ShopV2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ShopV2 shopV2 = new ShopV2();
        long start = System.nanoTime();
        Future<Double> futurePrice = shopV2.getErrorV1("product1");
//        Future<Double> futurePrice = shopV2.getErrorV2("product1");
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

    private Future<Double> getErrorV1(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            System.out.println("futurePrice = " + futurePrice);
            double price = errorPrice(product); // 만약 결과가 제대로 반환되지 않고, 에러가 발생하는 경우라면...
            futurePrice.complete(price);
        }).start();
        futurePrice.orTimeout(10, TimeUnit.SECONDS);    // 최소한의 Timeout 정도는 만들어두자.
        return futurePrice;
    }

    // 타임아웃을 만들면 안전해지긴 하나, 피드백 로그가 없다 => 이를 위해 completeExceptionally를 만들자
    private Future<Double> getErrorV2(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            try {
                System.out.println("futurePrice = " + futurePrice);
                double price = errorPrice(product);
                futurePrice.complete(price);
            } catch (Exception ex) {
                futurePrice.completeExceptionally(ex);   // Exception 발생 시 여기에 담아서 반환
            }
        }).start();
        return futurePrice;
    }

    // 일부러 Error 나오는 상황을 만들기
    private double errorPrice(String product) {
        delay();
        throw new RuntimeException();
    }

}
