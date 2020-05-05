package reactive.tutorial.example;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Launcher {
    //Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
    //Observable.just("Alpha", "Beta", "Zeta", "Eta", "Gamma", "Delta")
    //Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon", "Zeta", "Eta", "Theta", "Iota");
    //Observable.just(1, 1, 1, 2, 2, 3, 3, 2, 1, 1)
    public static void main(String[] args) {
        Observable<Notification<String>> source =
                Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
                .materialize()
                .publish()
                .autoConnect(3);

        source.filter(Notification::isOnNext)
                .subscribe(n -> System.out.println("onNext = " + n.getValue()));

        source.filter(Notification::isOnComplete)
                .subscribe(n -> System.out.println("onComplete"));

        source.filter(Notification::isOnError)
                .subscribe(n -> System.out.println("onError"));
    }

    private static void write(String value, String path) {
        BufferedWriter writer = null;
        File file = new File(path);
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.append(value);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getResponse(String path) {
        try {
            return new Scanner(new URL(path).openStream(), "UTF-8")
                    .useDelimiter("\\A").next();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public static <T> T intenseCalculation(T value) {
        sleep(ThreadLocalRandom.current().nextInt(3000));
        return value;
    }


    public static int randomInt() {
        return ThreadLocalRandom.current().nextInt(100000);
    }

    public static int randomSleepTime() {
        return ThreadLocalRandom.current().nextInt(2000);
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static final class MyItem {
        final int id;

        MyItem(int id) {
            this.id = id;
            System.out.println("Constructing MyItem " + id);
        }
    }
}