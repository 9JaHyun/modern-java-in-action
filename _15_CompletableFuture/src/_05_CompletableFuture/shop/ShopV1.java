package _05_CompletableFuture.shop;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


// 여기서 문제는.. Thread에 대한 예외처리가 제대로 되어 있지 않다는 것.
// getPriceAsync() Thread 내부에서 만약 에러가 발생한다면? => get() 결과가 나올때까지 영원히 버팀.
public class ShopV1 {

     public static void main(String[] args) throws ExecutionException, InterruptedException {
        ShopV1 shopV1 = new ShopV1();
        long start = System.nanoTime();
        Future<Double> futurePrice = shopV1.getPriceAsync("product1");  // 요청 Thread가 CompletableFuture Thread에게 위임
        long invocationTime = ((System.nanoTime() - start) / 1_000_000);        // 요청 Thread가 실행
        System.out.println("InvocationTime: " + invocationTime + "msecs");

        try {
            Double price = futurePrice.get();       // 이제 CompletableFuture Thread에게 맡긴 계산의 결과가 필요 (값을 줄때까지 Block)
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

    private Future<Double> getPriceAsync(String product) {
            CompletableFuture<Double> futurePrice = new CompletableFuture<>();
            new Thread(() -> {
                System.out.println("futurePrice = " + futurePrice);
                double price = calculatePrice(product); // 다른 스레드에서 비동기적으로 계산 수행
                futurePrice.complete(price); // 오래 걸리는 계산이 완료되면 Future 에 할당. 및 종료
            }).start();
            return futurePrice; // 계산 결과가 완료되길 기다리지 않고 Future 반환
    }

    private double calculatePrice(String product) {
        delay();
        return new Random().nextDouble() * product.charAt(0) + product.charAt(1);
    }
}
