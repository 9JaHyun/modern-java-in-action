package _02_ExecutorService_Interface;

import static java.lang.Thread.sleep;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.IntConsumer;

public class ExecutorServiceExample1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        usingThreadPool();
        usingReactiveAPI();
    }

    private static void usingThreadPool() throws InterruptedException, ExecutionException {
        ExecutorServiceExample1 es = new ExecutorServiceExample1();
        int x = 1337;

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> y = executorService.submit(() -> es.f(x));
        Future<Integer> z = executorService.submit(() -> es.g(x));

        System.out.println(y.get() + z.get());

        executorService.shutdown();
    }

    private static void usingReactiveAPI() throws InterruptedException, ExecutionException {
        ExecutorServiceExample1 es = new ExecutorServiceExample1();
        Result r = new Result();
        int x = 1337;

        es.newF(x, (int y) -> {
            r.left = y;
            System.out.println(r.left + r.right);
        });


        es.newG(x, (int z) -> {
            r.right = z;
            System.out.println(r.left + r.right);
        });
    }

    static class Result {
        private int left;
        private int right;
    }


    private int f(int x) throws InterruptedException {
        sleep(5000);
        System.out.println("f(x) = " + x);
        return x;
    }

    private int g(int x) throws InterruptedException {
        sleep(5000);
        System.out.println("g(x) = " + x);
        return x;
    }

    private void newF(int x, IntConsumer c){}

    private void newG(int x, IntConsumer c){}
}
