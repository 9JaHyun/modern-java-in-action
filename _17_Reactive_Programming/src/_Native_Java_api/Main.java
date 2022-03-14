package _Native_Java_api;

import java.util.concurrent.Flow.Publisher;

public class Main {

    public static void main(String[] args) {
        getTemperaturesV2("New York").subscribe(new TempSubscriber());
    }

    private static Publisher<TempInfo> getTemperatures(String town) {
        return subscriber -> subscriber.onSubscribe(
              new TempSubscription(subscriber, town)
        );
    }

    private static Publisher<TempInfo> getTemperaturesV2(String town) {
        return subscriber -> {
            TempProcessor processor = new TempProcessor();
            processor.subscribe(subscriber);
            processor.onSubscribe(new TempSubscription(processor, town));
        };
    }

}
