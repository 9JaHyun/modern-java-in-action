package _02_ExecutorService_Interface;

import static java.lang.Thread.sleep;

public class ThreadAndRunnable {

    public static void main(String[] args) throws InterruptedException {
        ThreadAndRunnable app = new ThreadAndRunnable();
        int x = 100;
        Result result = new Result();

        Thread t1 = new Thread(() -> {
            try {
                result.left = app.f(x);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                result.right = app.g(x);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // 병렬 실행
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(result.left + result.right);
    }

    private static class Result {
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
}
