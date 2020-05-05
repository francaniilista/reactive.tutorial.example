import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class RxTest1 {
    @Test
    public void usingTestObserver() {
        //An Observable with 5 one-second emissions
        Observable<Long> source = Observable.interval(1, TimeUnit.SECONDS)
                .take(5);

        //Declare TestObserver
        TestObserver<Long> testObserver = new TestObserver<>();

        //Assert no subscription has occurred yet
        testObserver.assertNotSubscribed();

        //Subscribe TestObserver to source
        source.subscribe(testObserver);

        //Assert TestObserver is subscribed
        testObserver.assertSubscribed();

        //Block and wait for Observable to terminate
        testObserver.awaitTerminalEvent();

        //Assert TestObserver called onComplete()
        testObserver.assertComplete();

        //Assert there were no errors
        testObserver.assertNoErrors();

        //Assert 5 values were received
        testObserver.assertValueCount(5);

        //Assert the received emissions were 0, 1, 2, 3, 4
        testObserver.assertValues(0l, 1l, 2l, 3l, 4l);
    }

    @Test
    public void usingTestObserverStreamlined() {
        //An Observable with 5 one-second emissions
        Observable<Long> source = Observable.interval(1, TimeUnit.SECONDS)
                .take(5);

        //Declare TestObserver
        TestObserver<Long> testObserver = new TestObserver<>();

        testObserver.assertNotSubscribed();

        //Subscribe TestObserver to source
        source.subscribe(testObserver);

        testObserver
                .assertSubscribed()
                .awaitTerminalEvent();

        testObserver
                .assertComplete()
                .assertNoErrors()
                .assertValueCount(5)
                .assertValues(0l, 1l, 2l, 3l, 4l);
    }

    @Test
    public void usingTesScheduler() {
        //Declare TestScheduler
        TestScheduler testScheduler = new TestScheduler();

        //Declare TestObserver
        TestObserver<Long> testObserver = new TestObserver<>();

        //Declare Observable emitting every 1 minute
        Observable<Long> minuteTicker = Observable.interval(1, TimeUnit.MINUTES, testScheduler);

        //Subscribe to TestObserver
        minuteTicker.subscribe(testObserver);

        //Fast forward by 30 seconds
        testScheduler.advanceTimeBy(30, TimeUnit.SECONDS);

        //Assert no emission have occurred yet
        testObserver.assertValueCount(0);

        //Fast forward 70 seconds after subscription
        testScheduler.advanceTimeTo(70, TimeUnit.SECONDS);

        //Assert the first emission has occurred
        testObserver.assertValueCount(1);

        //Fast forward to 90 minutes after subscription
        testScheduler.advanceTimeTo(90, TimeUnit.MINUTES);

        //Assert 90 emissions have occurred
        testObserver.assertValueCount(90);
    }
}
