package reactive.tutorial.example;

import io.reactivex.Emitter;
import io.reactivex.Flowable;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

public class Launcher1 {
    public static void main(String[] args) {
        Flowable<LocalDate> dates =
                Flowable
                        .generate(
                                () -> new AtomicReference<>(LocalDate.of(2017,1,1)),
                        (AtomicReference<LocalDate> next, Emitter<LocalDate> emitter) ->
                                emitter.onNext(next.getAndUpdate(dt -> dt.plusDays(1))));

        dates.take(60)
                .subscribe(System.out::println);

        sleep(5000);
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
