package _Native_Java_api;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class TempSubscription implements Subscription {

    private static final ExecutorService excutor = Executors.newSingleThreadExecutor();

    private final Subscriber<? super TempInfo> subscriber;
    private final String town;


    public TempSubscription(Subscriber<? super TempInfo> subscriber, String town) {
        this.subscriber = subscriber;
        this.town = town;
    }

    // RuntimeException 수정
//    @Override
//    public void request(long n) {
//        for (long i = 0L; i < n; i++) {
//            try {
//                subscriber.onNext(_java_api.TempInfo.fetch(town));
//            } catch (Exception e) {
//                subscriber.onError(e);
//                break;
//            }
//        }
//    }
    // Executor 생성
    @Override
    public void request(long n) {
        excutor.submit(() -> {
            for (long i = 0L; i < n; i++) {
                try {
                    subscriber.onNext(TempInfo.fetch(town));
                } catch (Exception e) {
                    subscriber.onError(e);
                    break;
                }
            }
        });
    }

    @Override
    public void cancel() {
        subscriber.onComplete();
    }
}
