import org.junit.Ignore;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.LongStream;

import static java.lang.String.format;

public class Schoenfinkeln {
    public static final String FILENAME = "/tmp/test.txt";

    private final Random random = new Random(System.nanoTime());

    @Test
    public void testCurrying() {
        final List<String> list = Arrays.asList("Hans", "Nina", "Petz", "Maja");

        final Function<PrintStream, Consumer<Object>> printTo = printStream -> object -> printStream.println(object);
        list.forEach(printTo.apply(System.out));

        final Function<String, OutputStream> fileOutputStream = name -> {
            try {
                return new FileOutputStream(name);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        };

        final OutputStream outputStream = fileOutputStream.apply(FILENAME);
        final Function<OutputStream, PrintStream> printStreamOf = PrintStream::new;
        list.forEach(printTo.compose(printStreamOf).apply(outputStream));
        list.forEach(printStreamOf.andThen(printTo).apply(outputStream));

        list.forEach(printTo.compose(printStreamOf).compose(fileOutputStream).apply(FILENAME));
        list.forEach(fileOutputStream.andThen(printStreamOf).andThen(printTo).apply(FILENAME));

        list.forEach(fileOutputStream.andThen(printStreamOf).andThen(printTo).apply(FILENAME)
                .andThen(printTo.apply(System.out)));
    }

    class Profiler<ResultType> {
        private final ResultType result;
        private final Duration duration;

        Profiler(Supplier<ResultType> operation) {
            Objects.requireNonNull(operation);
            final Temporal start = Instant.now();
            result = operation.get();
            final Temporal stop = Instant.now();
            duration = Duration.between(start, stop);
        }

        public String format(String template) {
            Objects.requireNonNull(template);
            return String.format(template, result, duration.toMillis());
        }
    }

    private boolean isSmall(double value) {
        return value < 1e-6;
    }

    @Test
    public void testSerialSum() throws Exception {
        final Profiler<Long> profiler = new Profiler<>(() -> LongStream.range(0, 1_000_000_000).sum());
        System.out.println(profiler.format("serialSum: %d, durationMs: %d"));
    }

    @Test
    public void testParallelSum() throws Exception {
        final Profiler<Long> profiler = new Profiler<>(() -> LongStream.range(0, 1_000_000_000).parallel().sum());
        System.out.println(profiler.format("parallelSum: %d, durationMs: %d"));
    }

    @Test
    public void testSerialFindFirst() throws Exception {
        final Profiler<Double> profiler = new Profiler<>(
                () -> random.doubles(1_000_000_000).filter(this::isSmall).findFirst().orElse(Double.NaN));
        System.out.println(profiler.format("serialFindFirst: %g, durationMs: %d"));
    }

    @Test
    public void testParallelFindFirst() throws Exception {
        final Profiler<Double> profiler = new Profiler<>(
                () -> random.doubles(1_000_000_000).parallel().filter(this::isSmall).findFirst().orElse(Double.NaN));
        System.out.println(profiler.format("parallelFindFirst: %g, durationMs: %d"));
    }

    @Test
    public void testSerialFindAny() throws Exception {
        final Profiler<Double> profiler = new Profiler<>(
                () -> random.doubles(1_000_000_000).filter(this::isSmall).findAny().orElse(Double.NaN));
        System.out.println(profiler.format("serialFindAny: %g, durationMs: %d"));
    }

    @Test
    public void testParallelFindAny() throws Exception {
        final Profiler<Double> profiler = new Profiler<>(
                () -> random.doubles(1_000_000_000).parallel().filter(this::isSmall).findAny().orElse(Double.NaN));
        System.out.println(profiler.format("parallelFindAny: %g, durationMs: %d"));
    }
}
