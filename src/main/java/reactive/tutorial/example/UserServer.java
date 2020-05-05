package reactive.tutorial.example;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

import java.util.Arrays;
import java.util.List;

public class UserServer {

    public static Observable<User> getUsers(List<String> names) {
        System.out.println("Fetching users by name");

        return Observable.create(emitter -> fetchingByName(emitter, names));
    }

    private static void fetchingByName(ObservableEmitter<User> emitter, List<String> names) {
        int count = 0;
//        while(count < 5) {
            names.forEach(name ->
                    users.stream()
                            .filter(user -> byName(name, user))
                            .forEach(emitter::onNext)
            );

            //if (count > 3) emitter.onError(new RuntimeException("Thrown an error"));

            count++;
//        }
    }

    private static boolean byName(String name, User user) {
        return user.getName().contains(name);
    }

    private static List<User> users = Arrays.asList(
            new User(1l, "Paulo França"),
            new User(2l, "Jeff Rosen "),
            new User(3l, "Joao Moraveis"),
            new User(4l, "Anna Sambros"),
            new User(5l, "Otavio Tarelho"),
            new User(6l, "Dave Owens"),
            new User(7l, "Lionel Mendonca"),
            new User(8l, "Michael Cassano"),
            new User(9l, "Renato Freitas"),
            new User(10l, "Tim Shanahan"),
            new User(11l, "Igor Mendes"),
            new User(12l, "Ricardo Ribeiro"));
}