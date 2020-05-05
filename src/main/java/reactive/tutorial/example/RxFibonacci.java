package reactive.tutorial.example;

import io.reactivex.Observable;

public class RxFibonacci {

    static Observable<Integer> fibs() {
        return Observable.create(subscriber -> {
            int prev = 0;
            int current = 0;

            subscriber.onNext(0);
            subscriber.onNext(1);

            while (true) {
                int oldPrev = prev;
                prev = current;
                current += oldPrev;

                subscriber.onNext(current);
            }
        });
    }
}
