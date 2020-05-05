package reactive.tutorial.example;

import io.reactivex.Observable;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) {
        fakeUserInput()
                .flatMapMaybe(x -> RxFibonacci.fibs().elementAt(x))
                .blockingSubscribe(line -> System.out.println(line));
     }

     public static Observable<Integer> fakeUserInput() {
        Random random = new Random();
        return Observable
                .intervalRange(0,20,500, 500, TimeUnit.MILLISECONDS)
                .concatMap(number ->
                        Observable.just(random.nextInt(20))
                            .delay(random.nextInt(500), TimeUnit.MILLISECONDS));
     }
}