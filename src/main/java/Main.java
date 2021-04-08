import net.openhft.chronicle.set.ChronicleSetBuilder;
import org.apache.log4j.BasicConfigurator;

import java.time.Instant;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {

    long testNormalSet() throws InterruptedException {
        final var set = new HashSet<Long>();
        return sumNumbers(set);
    }

    long testChroniclelSet() throws InterruptedException {
        final var set = ChronicleSetBuilder.of(Long.class)
                .entries(30_000_000)
                .create();
        return sumNumbers(set);
    }

    long sumNumbers(Set<Long> numbers) throws InterruptedException {
        final Random random = new Random();
        for (int i = 0; i < 30_000_000; i++) {
            numbers.add(random.nextLong());
            if (i % 1_000_000 == 0) Thread.sleep(1000);
        }
        return numbers.stream().reduce(0L, Long::sum);
    }

    public static void main(String[] args) throws InterruptedException {
        BasicConfigurator.configure();
        var start = Instant.now();
        //new Main().testNormalSet();
        new Main().testChroniclelSet();
        var end = Instant.now();
        var timeMilli = end.toEpochMilli() - start.toEpochMilli();
        System.out.println("Time to get finished in ms: " + timeMilli);
    }
}
