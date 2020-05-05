import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import org.junit.Test;

public class RxTest2 {
    @Test
    public void debugWalkthrough() {
        //Declare TestObserver
        TestObserver<String> testObserver = new TestObserver<>();

        //Source pushing three strings
        Observable<String> items = Observable.just("521934/2342/Foxtrot",
                "Bravo/12112/78886/Tango",
                "283242/4542/Whiskey/2348562");

        //Split and concatMap() on "/"
        items
                .concatMap(s -> Observable.fromArray(s.split("/")))
                .filter(s -> s.matches("[A-Za-z]+"))
                .subscribe(testObserver);

        System.out.println(testObserver.values());

        testObserver.assertValues("Foxtrot","Bravo","Tango","Whiskey");
    }
}
