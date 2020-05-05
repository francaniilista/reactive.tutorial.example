import io.reactivex.Observable;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertTrue;

public class RxTest {
    @Test
    public void testBlockingSubscribe() {
        AtomicInteger hitCount = new AtomicInteger();

        Observable<Long> source = Observable.interval(1, TimeUnit.SECONDS)
                .take(5);

        source.blockingSubscribe(i -> hitCount.incrementAndGet());

        assertTrue(hitCount.get() == 5);
    }

    @Test
    public void testFirst() {
        Observable<String> source = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Zeta");

        String firstWithLengthFour = source.filter(s -> s.length() == 4)
                .blockingFirst();

        assertTrue(firstWithLengthFour.equals("Beta"));
    }

    @Test
    public void testSingle() {
        Observable<String> source = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");

        List<String> allWithLengthFour = source.filter(s -> s.length() == 4)
                .toList()
                .blockingGet();

        assertTrue(allWithLengthFour.equals(Arrays.asList("Beta", "Zeta")));
    }

    @Test
    public void testIterable() {
        Observable<String> source = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Zeta");

        Iterable<String> allWithLengthFive = source
                .filter(s -> s.length() == 5)
                .blockingIterable();

        for (String s: allWithLengthFive) {
            assertTrue(s.length() == 5);
        }
    }

    @Test
    public void testBlockingForEach() {
        Observable<String> source = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Zeta");

        source.filter(s -> s.length() == 5)
                .blockingForEach(s -> assertTrue(s.length() == 5));
    }
}
