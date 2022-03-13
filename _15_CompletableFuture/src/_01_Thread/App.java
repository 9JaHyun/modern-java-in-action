package _01_Thread;

// Runnable, Callable 들을 Task 라 부름
public class App {

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> doSomething("Thread1: "));
        Thread thread2 = new Thread(() -> doSomething("Thread2: "));
        Thread thread3 = new Thread(() -> doSomething("Thread3: "));

        // thread1이 끝나야 thread2 출력
        System.out.println("========== Thread.run() ==========");
        thread1.run();
        thread2.run();
        thread3.run();

        System.out.println("========== Thread.start() ==========");
        // 동시 출력
        thread1.start();
        thread2.start();
        thread3.start();
    }

    private static void doSomething(String s) {
        for (int i = 0; i < 5; i++) {
            System.out.println(s + i);
        }
        System.out.println(s + "finish!");
    }
}