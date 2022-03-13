package _01_Thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UseThreadPool {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Future<?> f1 = executorService.submit(() -> doSomething("Thread1: "));
        Future<?> f2 = executorService.submit(() -> doSomething("Thread2: "));
        Future<?> f3 = executorService.submit(() -> doSomething("Thread3: "));

        // 동시 출력
        f1.get();
        f2.get();
        f3.get();
    }

    private static void doSomething(String s) {
        for (int i = 0; i < 5; i++) {
            System.out.println(s + i);
        }
        System.out.println(s + "finish!");
    }
}
