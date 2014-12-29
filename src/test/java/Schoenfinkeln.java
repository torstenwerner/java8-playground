import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class Schoenfinkeln {
    private final List<String> list = Arrays.asList("Hans", "Nina", "Petz", "Maja");
    private final String FILENAME = "/tmp/test.txt";

    @Test
    public void testJava4() throws Exception {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    @Test
    public void testJava5() throws Exception {
        for (String s : list) {
            System.out.println(s);
        }
    }

    @Test
    public void testAnonymousClass() throws Exception {
        // higher flexibility
        list.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
    }

    @Test
    public void testJava8() throws Exception {
        // shorter and invoke_dynamic()
        list.forEach((String item) -> { System.out.println(item); });
        list.forEach((String item) -> System.out.println(item));
        list.forEach((item) -> System.out.println(item));
        list.forEach(item -> System.out.println(item));
        list.forEach(System.out::println);
        list.forEach(this::printToSystemOut);
    }

    private void printToSystemOut(Object value) {
        System.out.println(value);
    }

    @Test
    public void testConsumer() throws Exception {
        final Consumer<String> printToSystemOut_ = System.out::println;
        list.forEach(printToSystemOut_);
    }

    @Test
    public void testHigherOrderFunction() throws Exception {
        list.forEach(printTo(System.out));
    }

    private Consumer<String> printTo(PrintStream printStream) {
        return value -> printStream.println(value); // or: printStream::println
    }

    @Test
    public void testCurrying() {
        final Function<PrintStream, Consumer<Object>> printTo_ = printStream -> value -> printStream.println(value);
        list.forEach(printTo_.apply(System.out));

        final Function<OutputStream, PrintStream> printStreamOf = PrintStream::new; // or: os -> new PrintStream(os)

        final Function<String, OutputStream> fileOutputStreamOf = fileName -> {
            try {
                return new FileOutputStream(fileName);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        };

        list.forEach(printTo_.compose(printStreamOf).compose(fileOutputStreamOf).apply(FILENAME));

        list.forEach(fileOutputStreamOf.andThen(printStreamOf).andThen(printTo_).apply(FILENAME));

        list.forEach(fileOutputStreamOf.andThen(printStreamOf).andThen(printTo_).apply(FILENAME)
                .andThen(printTo_.apply(System.out)));
    }
}
