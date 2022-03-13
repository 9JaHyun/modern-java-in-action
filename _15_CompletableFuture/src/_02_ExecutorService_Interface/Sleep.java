package _02_ExecutorService_Interface;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Sleep {

    public static void main(String[] args) throws InterruptedException {
        sleepV1();
//        sleepV2();
    }

    // 아무것도 하지 않고 계속 자원(스레드) 점유...
    private static void sleepV1() throws InterruptedException {
        doSomething1();
        Thread.sleep(5000);
        doSomething2();
    }

    // 다른 작업을 큐에 등록
    private static void sleepV2() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        doSomething1();
        // 10초 뒤 doSomething2()를 개별 태스크로 스케줄
        service.schedule(Sleep::doSomething2, 5, TimeUnit.SECONDS);
    }

    public static void doSomething1() {
        System.out.println("do something1...");
    }

    public static void doSomething2() {
        System.out.println("do something2...");
    }
}
