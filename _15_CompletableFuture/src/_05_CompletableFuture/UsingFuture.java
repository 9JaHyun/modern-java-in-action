package _05_CompletableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* 문제점
    Future가 여러개라면?? -> 간결한 동시 실행 코드를 구현하기가 어려움
        - 오래 걸리는 A가 끝나면 그 결과를 다른 오래 걸리는 계산 B로 전달
        - B의 결과가 나오면 다른 질의 결과와 B의 결과를 조합
    ==> 두 비동기 계산 결과를 합칠 선언형 기능이 필요
    ==> 모든 태스크의 완료를 체크하고 기다릴 기능이 필요
    ==> 비동기 동작의 결과를 수동으로 제공
    ==> Future 완료 Event에 반응 해 후처리 동작을 제공해야 함.

    이 문제를 해결하기 위해서 CompletableFuture가 탄생 (Stream이랑 비슷한 파이프라이닝 활용)

 */
public class UsingFuture {

    public static void main(String[] args) {
        UsingFuture u = new UsingFuture();
        u.usingFuture1();
    }

    private void usingFuture1() {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Double> future = executor.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return doSomeLongComputation(); // 오래 걸리는 작업
            }
        });
        doSomethingElse();  // 비동기 작업 수행 동안 다른 작업 실시

        try {
            // 비동기 작업의 결과를 가져옴 단, timeout안에 결과가 준비되어 있지 않으면 호출 스레드 block
            // 이렇게 최대 타임아웃 시간을 설정해주는 것이 좋다.
            Double result = future.get(1, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace(); // 계산 예외
        } catch (InterruptedException e) {
            e.printStackTrace(); // 현재 스레드에서 대기 중 인터럽트 발생
        } catch (TimeoutException e) {
            System.out.println("doSomeLongComputation이 너무 오래 걸립니다.");
            e.printStackTrace(); // Future가 완료되기 전 타임아웃 발생 ()
        }
//        executor.shutdown();
    }

    private Double doSomeLongComputation() {
        Double d = 1.0;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            d += i;
        }

        System.out.println("Long Computation done! (%f)".formatted(d));
        return d;
    }

    // 해당 연산이 너무 짧아 doSomeLongComputation()와 갭이 크면 TImeOut 발생!
    private void doSomethingElse() {
//        Long result = 0L;
        long result = 0L;
        for (long i = 0; i < Integer.MAX_VALUE; i++) {
            result += i;
        }
        System.out.println("do Something Else... " + result);

    }

}
