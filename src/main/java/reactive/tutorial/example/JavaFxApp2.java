package reactive.tutorial.example;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

public class JavaFxApp2 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        root.setMinSize(200, 100);

        Label typedTextLabel = new Label("");
        root.getChildren().addAll(typedTextLabel);
        Scene scene = new Scene(root);

        //Multicast typed keys
        Observable<String> typedLetters = JavaFxObservable
                .eventsOf(scene, KeyEvent.KEY_TYPED)
                .map(KeyEvent::getCharacter)
                .share();

        //Signal 500 milliseconds of inactivity
        Observable<String> restSignal = typedLetters
                .throttleWithTimeout(2000, TimeUnit.MILLISECONDS)
                .startWith(""); //trigger initial

        // switchMap() each period of inactivity to
        // an infinite scan() concatenating typed letters
        restSignal.switchMap(s -> typedLetters.scan("", (rolling, next) -> rolling + next))
                .observeOn(JavaFxScheduler.platform())
                .subscribe(s -> {
                    typedTextLabel.setText(s);
                    System.out.println(s);
                });

        stage.setScene(scene);
        stage.show();
    }
}
