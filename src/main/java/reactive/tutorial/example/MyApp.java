package reactive.tutorial.example;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MyApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        VBox vBox = new VBox();

        ObservableList<String> values = FXCollections.observableArrayList("Alpha", "Beta", "Gamma");

        Label valuesLabel = new Label("Values");
        ListView<String> valuesListView = new ListView<>(values);

        Label distinctLengthsLabel = new Label("Distinct Lengths");
        Label distinctLengthsConcatLabel = new Label();
        distinctLengthsConcatLabel.setTextFill(Color.RED);

        JavaFxObservable.emitOnChanged(values)
                .flatMapSingle(list ->
                        Observable.fromIterable(list)
                                .map(String::length)
                                .distinct()
                                .reduce("", (x,y) -> x + (x.equals("") ? "" : "|") + y))
                .subscribe(distinctLengthsConcatLabel::setText);

        TextField inputField = new TextField();
        Button addButton = new Button("Add");

        JavaFxObservable.actionEventsOf(addButton)
                .map(e -> inputField.getText())
                .filter(s -> s != null && !s.trim().isEmpty())
                .subscribe(s -> {
                    values.add(s);
                    inputField.clear();
                });

        vBox.getChildren().addAll(valuesLabel,valuesListView,distinctLengthsLabel,
                distinctLengthsConcatLabel,inputField,addButton);
        stage.setScene(new Scene(vBox));
        stage.show();
    }
}