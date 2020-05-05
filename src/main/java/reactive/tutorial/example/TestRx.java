package reactive.tutorial.example;

import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

public class TestRx {
    public static void main(String[] args) {
//        Observable<String> names = Observable.just("Paulo", "Thays", "Helena", "Marina");
//        names.subscribe(
//                s -> System.out.println(s),
//                Throwable::printStackTrace,
//                () -> System.out.println("The stream is processed"));


        System.out.println("--------");

        String[] devs = {"Paulo", "Hugo", "Joao", "Otavio", "Renato", "Candido"};
        String[] qas  = {"Anna", "Lionel"};
        Observable<String[]> acquaTeam = Observable.fromIterable(Arrays.asList(devs,qas));
        acquaTeam
                .map(names -> {
                    StringBuilder concatnames = new StringBuilder();
                    for (String teamMate : names) {
                        concatnames.append(" ");
                        concatnames.append(teamMate);
                    }
                    return concatnames.toString();
                })
                .subscribe(s -> System.out.println(s));


        System.out.println("--------");

        List<String> words = Arrays.asList(
                "the",
                "quick",
                "brown",
                "fox",
                "jumped",
                "over",
                "the",
                "lazy",
                "dog"
        );
        Observable.fromIterable(words)
                .zipWith(Observable.range(1, Integer.MAX_VALUE),
                        (string, count)->String.format("%2d. %s", count, string))
 .subscribe(System.out::println);
    }
}