package reactive.tutorial.example;

import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

public class HelloWorldAsync {
    public static void main(String[] args) {
        System.out.println("Ready to get users from server");
        List<String> names = Arrays.asList("Paulo", "Tim", "Dave");

        Observable<User> users = UserServer.getUsers(names);
        System.out.println("Got observable...");

        users.subscribe(System.out::println,
                throwable -> {
                    System.out.println("Will throw an exception");
                    System.out.println(throwable.getMessage());
                });
    }
}
