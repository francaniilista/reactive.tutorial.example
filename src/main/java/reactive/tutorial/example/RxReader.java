package reactive.tutorial.example;

import io.reactivex.Observable;

import java.io.BufferedReader;

class RxReader {
    Observable<String> lines(BufferedReader reader) {
        return Observable.create(subscriber -> {
            String line;

            while ((line = reader.readLine()) != null) {
                subscriber.onNext(line);

                if (subscriber.isDisposed()) break;
            }

            subscriber.onComplete();
        });
    }
}
